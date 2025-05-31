package org.dainn.userservice.service;

import org.dainn.userservice.dto.invitation.InvitationDto;
import org.dainn.userservice.dto.invitation.InvitationReq;
import org.dainn.userservice.dto.invitation.UserInfo;
import org.dainn.userservice.dto.user.UserDto;
import org.dainn.userservice.util.enums.InvitationStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IInvitationService {
    InvitationDto create(InvitationDto dto);
    String verify(UserInfo userInfo);
    void updateStatus(String id, InvitationStatus status);
    InvitationDto findById(String id);
    InvitationDto findByEmailPending(String email);
    void delete(String email);
    void deleteById(String id);
    List<InvitationDto> findAllByAgency(String id);
    Page<InvitationDto> findAll(InvitationReq request);
    void deleteByAgencyId(String agencyId);
    int countTeamMembers(String agencyId);
}
