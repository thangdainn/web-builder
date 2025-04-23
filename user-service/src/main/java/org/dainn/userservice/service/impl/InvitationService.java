package org.dainn.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.userservice.dto.invitation.InvitationDto;
import org.dainn.userservice.dto.invitation.InvitationReq;
import org.dainn.userservice.dto.invitation.UserInfo;
import org.dainn.userservice.dto.mail.MailData;
import org.dainn.userservice.dto.user.UserDto;
import org.dainn.userservice.event.EventProducer;
import org.dainn.userservice.exception.AppException;
import org.dainn.userservice.exception.ErrorCode;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvitationService implements IInvitationService {
    private final IInvitationRepository invitationRepository;
    private final IInvitationMapper invitationMapper;
    private final IUserService userService;
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;
    private final EventProducer eventProducer;

    @Value("${app.domain}")
    private String domain;

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
        MailData mailData = MailData.builder()
                .inviteId(entity.getId())
                .from(user.getEmail())
                .to(dto.getEmail())
                .subject("Your Invitation to Join Web builder")
                .inviterName(user.getName())
                .invitationLink(domain + "/sign-in")
                .build();
        eventProducer.sendInviteEvent(mailData);
        return invitationMapper.toDto(invitationRepository.save(entity));


    }

    @Transactional
    @Override
    public String verify(UserInfo userInfo) {
        Optional<Invitation> invitationOptional = invitationRepository.findByEmailAndStatus(userInfo.getEmail(), InvitationStatus.PENDING);
        if (invitationOptional.isEmpty()) {
            Optional<User> optional = userRepository.findByEmail(userInfo.getEmail());
            if (optional.isPresent()) {
                return optional.get().getAgencyId();
            }
            return null;
        }
        Invitation invitation = invitationOptional.get();
        UserDto dto = new UserDto();
        dto = userMapper.toUserInfo(dto, userInfo);
        dto.setId(userInfo.getId());
        dto.setEmail(invitation.getEmail());
        dto.setName(userInfo.getName());
        dto.setAvatarUrl(userInfo.getAvatarUrl());
        dto.setRole(invitation.getRole());
        dto.setAgencyId(invitation.getAgencyId());

        dto = userService.create(dto);
        invitationRepository.deleteById(invitation.getId());
        log.info("Invitation verified");
        eventProducer.changeAgencyEvent(dto.getEmail());
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
