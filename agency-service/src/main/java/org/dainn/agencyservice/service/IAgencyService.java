package org.dainn.agencyservice.service;


import org.dainn.agencyservice.dto.*;

public interface IAgencyService {
    AgencyDto create(CreateAgencyDto dto);
    AgencyDto update(AgencyDto dto);
    AgencyDto updateGoal(String id, UpdateGoalDto dto);
    AgencyDto findById(String id);
    AgencyDetailDto findDetailById(String id);
    AgencyDetailDto findByCustomerId(String id);
    void delete(DeleteAgencyDto dto);
    void updateConnectAccId(String id, String connectAccId);
    void updateCustomerId(String id, String customerId);
}
