package org.dainn.userservice.service;

import org.dainn.userservice.dto.invitation.InvitationDto;
import org.dainn.userservice.dto.invitation.InvitationReq;
import org.dainn.userservice.dto.user.UserDto;
import org.dainn.userservice.util.enums.InvitationStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IInvitationService {
    InvitationDto create(InvitationDto dto);
    void verify(String email, UserDto userInfo);
    void updateStatus(String id, InvitationStatus status);
    InvitationDto findById(String id);
    InvitationDto findByEmailPending(String email);
    void delete(String email);
    List<InvitationDto> findAllByAgency(String id);
    Page<InvitationDto> findAll(InvitationReq request);
}
