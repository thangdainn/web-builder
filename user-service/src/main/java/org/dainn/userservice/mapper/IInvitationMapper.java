package org.dainn.userservice.mapper;

import org.dainn.userservice.dto.invitation.InvitationDto;
import org.dainn.userservice.model.Invitation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInvitationMapper {
    InvitationDto toDto(Invitation entity);
    Invitation toEntity(InvitationDto dto);
}
