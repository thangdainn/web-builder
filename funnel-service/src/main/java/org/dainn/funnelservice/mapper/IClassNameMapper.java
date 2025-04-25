package org.dainn.funnelservice.mapper;

import org.dainn.funnelservice.dto.ClassNameDto;
import org.dainn.funnelservice.model.ClassName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IClassNameMapper {
    ClassNameDto toDto(ClassName entity);

    ClassName toEntity(ClassNameDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "funnelId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ClassName toUpdate(@MappingTarget ClassName entity, ClassNameDto dto);
}
