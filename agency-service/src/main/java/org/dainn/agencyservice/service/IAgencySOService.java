package org.dainn.agencyservice.service;


import org.dainn.agencyservice.dto.AgencySODto;

import java.util.List;

public interface IAgencySOService {
    List<AgencySODto> create(List<AgencySODto> list, String agencyId);
    AgencySODto findById(String id);
    AgencySODto update(AgencySODto dto);
    void delete(String id);
}
