package org.dainn.funnelservice.mapper;

import org.dainn.funnelservice.dto.FunnelPageDto;
import org.dainn.funnelservice.model.FunnelPage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IFunnelPageMapper {
    @Mapping(target = "funnelId", source = "funnel.id")
    FunnelPageDto toDto(FunnelPage entity);

    FunnelPage toEntity(FunnelPageDto dto);
}
