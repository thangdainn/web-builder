package org.dainn.subaccountservice.mapper;

import org.dainn.subaccountservice.dto.SubAccountDto;
import org.dainn.subaccountservice.model.SubAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ISubAccountMapper {
    SubAccountDto toDto(SubAccount entity);
    SubAccount toEntity(SubAccountDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SubAccount toUpdate(@MappingTarget SubAccount entity, SubAccountDto dto);
}
