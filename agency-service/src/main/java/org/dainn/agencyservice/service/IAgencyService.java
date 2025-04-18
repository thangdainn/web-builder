package org.dainn.agencyservice.service;


import org.dainn.agencyservice.dto.AgencyDetailDto;
import org.dainn.agencyservice.dto.AgencyDto;
import org.dainn.agencyservice.dto.CreateAgencyDto;
import org.dainn.agencyservice.dto.UpdateGoalDto;

public interface IAgencyService {
    AgencyDto create(CreateAgencyDto dto);
    AgencyDto update(AgencyDto dto);
    AgencyDto updateGoal(String id, UpdateGoalDto dto);
    AgencyDetailDto findById(String id);
    AgencyDetailDto findByCustomerId(String id);
    void delete(String id);
    void updateConnectAccId(String id, String connectAccId);
}
