package org.dainn.funnelservice.mapper;

import org.dainn.funnelservice.dto.FunnelPageDto;
import org.dainn.funnelservice.dto.UpdateFPDto;
import org.dainn.funnelservice.model.FunnelPage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IFunnelPageMapper {
    FunnelPageDto toDto(FunnelPage entity);

    FunnelPage toEntity(FunnelPageDto dto);

    @Mapping(target = "id", ignore = true)
    FunnelPage toUpdate(@MappingTarget FunnelPage entity, UpdateFPDto dto);
}
