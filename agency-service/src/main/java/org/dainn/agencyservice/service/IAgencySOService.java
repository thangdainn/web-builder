package org.dainn.agencyservice.service;


import org.dainn.agencyservice.dto.AgencySODto;

public interface IAgencySOService {
    AgencySODto create(AgencySODto dto);
    AgencySODto findById(String id);
    AgencySODto update(AgencySODto dto);
    void delete(String id);
}
