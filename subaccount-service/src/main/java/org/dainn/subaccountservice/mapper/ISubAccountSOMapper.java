package org.dainn.subaccountservice.mapper;

import org.dainn.subaccountservice.dto.SubAccountSODto;
import org.dainn.subaccountservice.model.SubAccountSidebarOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ISubAccountSOMapper {
    @Mapping(target = "subAccountId", source = "subAccount.id")
    SubAccountSODto toDto(SubAccountSidebarOption entity);
    SubAccountSidebarOption toEntity(SubAccountSODto dto);
}
