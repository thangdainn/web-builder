package org.dainn.pipelineservice.mapper;

import org.dainn.pipelineservice.dto.LaneDto;
import org.dainn.pipelineservice.model.Lane;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ILaneMapper {
    @Mapping(target = "pipelineId", source = "pipeline.id")
    LaneDto toDto(Lane entity);

    Lane toEntity(LaneDto dto);
}
