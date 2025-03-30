package org.dainn.agencyservice.service;


import org.dainn.agencyservice.dto.AgencyDto;

public interface IAgencyService {
    AgencyDto create(AgencyDto dto);
    AgencyDto update(AgencyDto dto);
    AgencyDto findById(String id);
    void delete(String id);
}
