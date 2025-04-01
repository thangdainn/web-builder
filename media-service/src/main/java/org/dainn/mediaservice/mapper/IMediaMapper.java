package org.dainn.mediaservice.mapper;

import org.dainn.mediaservice.dto.MediaDto;
import org.dainn.mediaservice.model.Media;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IMediaMapper {
    MediaDto toDto(Media entity);
    Media toEntity(MediaDto dto);
}
