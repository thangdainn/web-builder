package org.dainn.agencyservice.mapper;

import org.dainn.agencyservice.dto.AgencyDetailDto;
import org.dainn.agencyservice.dto.AgencyDto;
import org.dainn.agencyservice.dto.CreateAgencyDto;
import org.dainn.agencyservice.model.Agency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IAgencyMapper {
    AgencyDto toDto(Agency entity);
    AgencyDetailDto toDetail(Agency entity);
    Agency toEntity(AgencyDto dto);
    Agency toEntity(CreateAgencyDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Agency update(@MappingTarget Agency entity, AgencyDto dto);
}
