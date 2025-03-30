package org.dainn.agencyservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.agencyservice.dto.AgencyDto;
import org.dainn.agencyservice.exception.AppException;
import org.dainn.agencyservice.exception.ErrorCode;
import org.dainn.agencyservice.mapper.IAgencyMapper;
import org.dainn.agencyservice.repository.IAgencyRepository;
import org.dainn.agencyservice.service.IAgencyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AgencyService implements IAgencyService {
    private final IAgencyRepository agencyRepository;
    private final IAgencyMapper agencyMapper;

    @Transactional
    @Override
    public AgencyDto create(AgencyDto dto) {
        return agencyMapper.toDto(agencyRepository.save(agencyMapper.toEntity(dto)));
    }

    @Transactional
    @Override
    public AgencyDto update(AgencyDto dto) {
        return null;
    }

    @Override
    public AgencyDto findById(String id) {
        return agencyMapper.toDto(agencyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AGENCY_NOT_EXISTED)));
    }

    @Override
    public void delete(String id) {

    }
}
