package org.dainn.agencyservice.mapper;

import org.dainn.agencyservice.dto.AgencyDto;
import org.dainn.agencyservice.model.Agency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAgencyMapper {
    AgencyDto toDto(Agency entity);
    Agency toEntity(AgencyDto dto);
}
