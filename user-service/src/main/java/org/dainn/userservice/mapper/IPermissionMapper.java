package org.dainn.userservice.mapper;

import org.dainn.userservice.dto.event.UserProducer;
import org.dainn.userservice.dto.permission.PermissionDto;
import org.dainn.userservice.model.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IPermissionMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "subAccountId", source = "subAccount.id")
    PermissionDto toDto(Permission entity);
    Permission toEntity(PermissionDto dto);

    UserProducer.Permission toProducer(Permission entity);
}
