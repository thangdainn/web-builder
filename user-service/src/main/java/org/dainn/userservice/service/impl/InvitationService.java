package org.dainn.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.userservice.dto.invitation.InvitationDto;
import org.dainn.userservice.dto.invitation.InvitationReq;
import org.dainn.userservice.dto.invitation.UserInfo;
import org.dainn.userservice.dto.mail.MailData;
import org.dainn.userservice.dto.user.UserDto;
import org.dainn.userservice.event.EventProducer;
import org.dainn.userservice.exception.AppException;
import org.dainn.userservice.exception.ErrorCode;
import org.dainn.userservice.jwt.JwtProvider;
import org.dainn.userservice.mapper.IInvitationMapper;
import org.dainn.userservice.mapper.IUserMapper;
import org.dainn.userservice.model.Invitation;
import org.dainn.userservice.model.User;
import org.dainn.userservice.repository.IInvitationRepository;
import org.dainn.userservice.repository.IUserRepository;
import org.dainn.userservice.service.IInvitationService;
import org.dainn.userservice.service.IUserService;
import org.dainn.userservice.util.Paging;
import org.dainn.userservice.util.enums.InvitationStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvitationService implements IInvitationService {
    private final IInvitationRepository invitationRepository;
    private final IInvitationMapper invitationMapper;
    private final IUserService userService;
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;
    private final EventProducer eventProducer;
    private final JwtProvider jwtProvider;

    @Transactional
    @Override
    public InvitationDto create(InvitationDto dto) {
        invitationRepository.findByEmail(dto.getEmail())
                .ifPresent(invitation -> {
                    throw new AppException(ErrorCode.INVITATION_EXISTED);
                });
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.EMAIL_EXISTED);
                });
        Invitation entity = invitationMapper.toEntity(dto);
        entity = invitationRepository.save(entity);
        UserDto user = userService.findOwnerByAgency(dto.getAgencyId());
        String token = jwtProvider.generateToken(dto.getEmail(), entity.getAgencyId());
        MailData mailData = MailData.builder()
                .inviteId(entity.getId())
                .from(user.getEmail())
                .to(dto.getEmail())
                .subject("Your Invitation to Join Web builder")
                .inviterName(user.getName())
                .invitationLink("http://localhost:3000/accept-invite?token=" + token)
                .build();
        eventProducer.sendInviteEvent(mailData);
        return invitationMapper.toDto(invitationRepository.save(entity));


    }

    @Transactional
    @Override
    public String verify(UserInfo userInfo) {
        if (!jwtProvider.validateToken(userInfo.getToken())) {
            return null;
        }
        String email = jwtProvider.extractEmail(userInfo.getToken());
        Invitation invitation = invitationRepository.findByEmailAndStatus(email, InvitationStatus.PENDING)
                .orElseThrow(() -> new AppException(ErrorCode.INVITATION_NOT_EXISTED));
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            return optional.get().getAgencyId();
        }
        UserDto dto = new UserDto();
        dto.setEmail(email);
        dto.setName(userInfo.getName());
        dto.setAvatarUrl(userInfo.getAvatarUrl());
        dto.setRole(invitation.getRole());
        dto.setAgencyId(invitation.getAgencyId());

        userService.create(dto);
        invitationRepository.deleteById(invitation.getId());
        return invitation.getAgencyId();
    }

    @Transactional
    @Override
    public void delete(String email) {
        invitationRepository.deleteByEmail(email);
    }

    @Override
    public List<InvitationDto> findAllByAgency(String id) {
        return invitationRepository.findAllByAgencyId(id)
                .stream()
                .map(invitationMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void updateStatus(String id, InvitationStatus status) {
        invitationRepository.updateStatus(id, status);
    }

    @Override
    public InvitationDto findById(String id) {
        return invitationMapper.toDto(invitationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVITATION_NOT_EXISTED)));
    }

    @Override
    public InvitationDto findByEmailPending(String email) {
        return invitationMapper.toDto(invitationRepository.findByEmailAndStatus(email, InvitationStatus.PENDING)
                .orElseThrow(() -> new AppException(ErrorCode.INVITATION_NOT_EXISTED)));
    }

    @Override
    public Page<InvitationDto> findAll(InvitationReq request) {
        return invitationRepository.findAll(Paging.getPageable(request))
                .map(invitationMapper::toDto);
    }
}
