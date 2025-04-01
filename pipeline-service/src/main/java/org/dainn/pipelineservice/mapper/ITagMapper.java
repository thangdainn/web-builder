package org.dainn.pipelineservice.mapper;

import org.dainn.pipelineservice.dto.lane.LaneDto;
import org.dainn.pipelineservice.dto.tag.TagDto;
import org.dainn.pipelineservice.model.Lane;
import org.dainn.pipelineservice.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ITagMapper {
    TagDto toDto(Tag entity);

    Tag toEntity(TagDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Tag toUpdate(@MappingTarget Tag entity, TagDto dto);
}
