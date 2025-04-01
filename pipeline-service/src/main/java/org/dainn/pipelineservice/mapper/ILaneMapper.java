package org.dainn.pipelineservice.mapper;

import org.dainn.pipelineservice.dto.lane.LaneDetailDto;
import org.dainn.pipelineservice.dto.lane.LaneDto;
import org.dainn.pipelineservice.model.Lane;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ILaneMapper {
    @Mapping(target = "pipelineId", source = "pipeline.id")
    LaneDto toDto(Lane entity);

    Lane toEntity(LaneDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Lane toUpdate(@MappingTarget Lane entity, LaneDto dto);

    @Mapping(target = "pipelineId", source = "pipeline.id")
    LaneDetailDto toDetail(Lane entity);
}
