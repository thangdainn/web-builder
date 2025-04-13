package org.dainn.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.userservice.dto.invitation.InvitationDto;
import org.dainn.userservice.dto.invitation.InvitationReq;
import org.dainn.userservice.dto.mail.MailData;
import org.dainn.userservice.dto.user.UserDto;
import org.dainn.userservice.event.EventProducer;
import org.dainn.userservice.exception.AppException;
import org.dainn.userservice.exception.ErrorCode;
import org.dainn.userservice.mapper.IInvitationMapper;
import org.dainn.userservice.mapper.IUserMapper;
import org.dainn.userservice.model.Invitation;
import org.dainn.userservice.repository.IInvitationRepository;
import org.dainn.userservice.service.IInvitationService;
import org.dainn.userservice.service.IUserService;
import org.dainn.userservice.util.Paging;
import org.dainn.userservice.util.enums.InvitationStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitationService implements IInvitationService {
    private final IInvitationRepository invitationRepository;
    private final IInvitationMapper invitationMapper;
    private final IUserService userService;
    private final IUserMapper userMapper;
    private final EventProducer eventProducer;

    @Transactional
    @Override
    public InvitationDto create(InvitationDto dto) {
        invitationRepository.findByEmail(dto.getEmail())
                .ifPresent(invitation -> {
                    throw new AppException(ErrorCode.INVITATION_EXISTED);
                });
        Invitation entity = invitationMapper.toEntity(dto);
        entity = invitationRepository.save(entity);
        MailData mailData = MailData.builder()
                .to(dto.getEmail())
                .subject("Your Invitation to Join Web builder")
                .inviterName("Dainn")
                .invitationLink("http://localhost:3000")
                .agencyId(entity.getAgencyId())
                .build();
        eventProducer.sendInviteEvent(mailData);
        return invitationMapper.toDto(invitationRepository.save(entity));


    }

    @Transactional
    @Override
    public void verify(String email, UserDto userInfo) {
        Invitation invitation = invitationRepository.findByEmailAndStatus(email, InvitationStatus.PENDING)
                .orElseThrow(() -> new AppException(ErrorCode.INVITATION_NOT_EXISTED));
        userInfo = userMapper.toUserInfo(userInfo, invitation);
        userService.create(userInfo);
        invitationRepository.deleteById(invitation.getId());
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
