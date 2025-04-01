package org.dainn.agencyservice.service;


import org.dainn.agencyservice.dto.AgencyDetailDto;
import org.dainn.agencyservice.dto.AgencyDto;
import org.dainn.agencyservice.dto.UpdateGoalDto;

public interface IAgencyService {
    AgencyDto create(AgencyDto dto);
    AgencyDto update(AgencyDto dto);
    AgencyDto updateGoal(String id, UpdateGoalDto dto);
    AgencyDetailDto findById(String id);
    void delete(String id);
}
