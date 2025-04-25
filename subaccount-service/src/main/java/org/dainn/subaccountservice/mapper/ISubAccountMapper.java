package org.dainn.subaccountservice.mapper;

import org.dainn.subaccountservice.dto.subaccount.CreateSubAccount;
import org.dainn.subaccountservice.dto.subaccount.SubAccountDetailDto;
import org.dainn.subaccountservice.dto.subaccount.SubAccountDto;
import org.dainn.subaccountservice.dto.subaccount.UpdateSubAccount;
import org.dainn.subaccountservice.model.SubAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ISubAccountMapper {
    SubAccountDto toDto(SubAccount entity);
    SubAccountDetailDto toDetailDto(SubAccount entity);
    SubAccount toEntity(SubAccountDetailDto dto);
    SubAccount toEntity(CreateSubAccount dto);

    SubAccount toUpdate(@MappingTarget SubAccount entity, UpdateSubAccount dto);
}
