package org.dainn.agencyservice.mapper;

import org.dainn.agencyservice.dto.AgencySODto;
import org.dainn.agencyservice.model.AgencySidebarOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IAgencySOMapper {
    @Mapping(target = "agencyId", source = "agency.id")
    AgencySODto toDto(AgencySidebarOption entity);

    AgencySidebarOption toEntity(AgencySODto dto);
}
