package org.dainn.userservice.mapper;

import org.dainn.userservice.dto.subaccount.SubAccountSODto;
import org.dainn.userservice.model.SubAccountSidebarOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ISubAccountSOMapper {
    @Mapping(target = "subAccountId", source = "subAccount.id")
    SubAccountSODto toDto(SubAccountSidebarOption entity);
    SubAccountSidebarOption toEntity(SubAccountSODto dto);
}
