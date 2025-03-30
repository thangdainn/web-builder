package org.dainn.subaccountservice.mapper;

import org.dainn.subaccountservice.dto.SubAccountDto;
import org.dainn.subaccountservice.model.SubAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ISubAccountMapper {
    SubAccountDto toDto(SubAccount entity);
    SubAccount toEntity(SubAccountDto dto);
}
