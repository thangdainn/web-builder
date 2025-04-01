package org.dainn.pipelineservice.mapper;

import org.dainn.pipelineservice.dto.pipeline.PipelineDto;
import org.dainn.pipelineservice.model.Pipeline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IPipelineMapper {
    PipelineDto toDto(Pipeline entity);
    Pipeline toEntity(PipelineDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subAccountId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Pipeline toUpdate(@MappingTarget Pipeline entity, PipelineDto dto);
}
