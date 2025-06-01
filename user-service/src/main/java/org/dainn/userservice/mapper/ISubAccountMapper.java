package org.dainn.userservice.mapper;

import org.dainn.userservice.dto.event.UserProducer;
import org.dainn.userservice.dto.subaccount.CreateSubAccount;
import org.dainn.userservice.dto.subaccount.SubAccountDetailDto;
import org.dainn.userservice.dto.subaccount.SubAccountDto;
import org.dainn.userservice.dto.subaccount.UpdateSubAccount;
import org.dainn.userservice.model.SubAccount;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ISubAccountMapper {
    SubAccountDto toDto(SubAccount entity);
    SubAccountDetailDto toDetailDto(SubAccount entity);
    SubAccount toEntity(SubAccountDetailDto dto);
    SubAccount toEntity(CreateSubAccount dto);

    SubAccount toUpdate(@MappingTarget SubAccount entity, UpdateSubAccount dto);

    UserProducer.SubAccount toProducer(SubAccountDetailDto dto);
}
