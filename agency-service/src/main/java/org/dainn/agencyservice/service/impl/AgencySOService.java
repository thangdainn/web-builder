package org.dainn.agencyservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.agencyservice.dto.AgencySODto;
import org.dainn.agencyservice.mapper.IAgencySOMapper;
import org.dainn.agencyservice.repository.IAgencyRepository;
import org.dainn.agencyservice.service.IAgencySOService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgencySOService implements IAgencySOService {
    private final IAgencyRepository agencyRepository;
    private final IAgencySOMapper agencySOMapper;


    @Override
    public AgencySODto create(AgencySODto dto) {
        return null;
    }

    @Override
    public AgencySODto findById(String id) {
        return null;
    }

    @Override
    public AgencySODto update(AgencySODto dto) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
