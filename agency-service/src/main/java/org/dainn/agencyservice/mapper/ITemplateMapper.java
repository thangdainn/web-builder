package org.dainn.agencyservice.mapper;

import org.dainn.agencyservice.dto.TemplateDto;
import org.dainn.agencyservice.model.Template;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITemplateMapper {
    TemplateDto toDto(Template entity);
    Template toEntity(TemplateDto dto);
}
