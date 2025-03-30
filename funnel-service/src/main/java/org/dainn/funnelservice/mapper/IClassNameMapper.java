package org.dainn.funnelservice.mapper;

import org.dainn.funnelservice.dto.ClassNameDto;
import org.dainn.funnelservice.model.ClassName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IClassNameMapper {
    @Mapping(target = "funnelId", source = "funnel.id")
    ClassNameDto toDto(ClassName entity);

    ClassName toEntity(ClassNameDto dto);
}
