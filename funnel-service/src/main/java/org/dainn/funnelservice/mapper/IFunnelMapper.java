package org.dainn.funnelservice.mapper;

import org.dainn.funnelservice.dto.FunnelDto;
import org.dainn.funnelservice.model.Funnel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IFunnelMapper {
    FunnelDto toDto(Funnel entity);
    Funnel toEntity(FunnelDto dto);
}
