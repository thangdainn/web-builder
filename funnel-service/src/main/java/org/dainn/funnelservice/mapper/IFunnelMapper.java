package org.dainn.funnelservice.mapper;

import org.dainn.funnelservice.dto.funnel.FunnelDetailDto;
import org.dainn.funnelservice.dto.funnel.FunnelDto;
import org.dainn.funnelservice.model.Funnel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IFunnelMapper {
    FunnelDto toDto(Funnel entity);
    Funnel toEntity(FunnelDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Funnel toUpdate(@MappingTarget Funnel entity, FunnelDto dto);

    FunnelDetailDto toDetail(Funnel entity);
}
