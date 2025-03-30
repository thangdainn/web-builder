package org.dainn.subscriptionservice.mapper;

import org.dainn.subscriptionservice.dto.AddOnsDto;
import org.dainn.subscriptionservice.model.AddOns;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IAddOnsMapper {
    AddOnsDto toDto(AddOns entity);
    AddOns toEntity(AddOnsDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AddOns updateEntity(@MappingTarget AddOns entity, AddOnsDto dto);
}
