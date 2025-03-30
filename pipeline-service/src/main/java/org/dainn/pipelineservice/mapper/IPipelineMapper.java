package org.dainn.pipelineservice.mapper;

import org.dainn.pipelineservice.dto.PipelineDto;
import org.dainn.pipelineservice.model.Pipeline;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPipelineMapper {
    PipelineDto toDto(Pipeline entity);
    Pipeline toEntity(PipelineDto dto);
}
