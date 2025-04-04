package org.dainn.agencyservice.service;


import org.dainn.agencyservice.dto.AgencySODto;
import org.dainn.agencyservice.model.Agency;

import java.util.List;

public interface IAgencySOService {
    List<AgencySODto> create(Agency agency);
    AgencySODto update(AgencySODto dto);
    AgencySODto findById(String id);
    void delete(String id);

    List<AgencySODto> findByAgency(String agencyId);
}
