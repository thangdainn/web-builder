package org.dainn.agencyservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.agencyservice.dto.AgencySODto;
import org.dainn.agencyservice.exception.AppException;
import org.dainn.agencyservice.exception.ErrorCode;
import org.dainn.agencyservice.mapper.IAgencySOMapper;
import org.dainn.agencyservice.model.Agency;
import org.dainn.agencyservice.model.AgencySidebarOption;
import org.dainn.agencyservice.repository.IAgencyRepository;
import org.dainn.agencyservice.repository.IAgencySORepository;
import org.dainn.agencyservice.service.IAgencySOService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgencySOService implements IAgencySOService {
    private final IAgencyRepository agencyRepository;
    private final IAgencySORepository agencySORepository;
    private final IAgencySOMapper agencySOMapper;

    @Transactional
    @Override
    public List<AgencySODto> create(List<AgencySODto> list, String agencyId) {
        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new AppException(ErrorCode.AGENCY_NOT_EXISTED));
        List<AgencySidebarOption> options = list.stream().map(agencySODto -> {
            AgencySidebarOption option = agencySOMapper.toEntity(agencySODto);
            option.setAgency(agency);
            return option;
        }).toList();
        options = agencySORepository.saveAll(options);
        return options.stream().map(agencySOMapper::toDto).toList();
    }

    @Override
    public AgencySODto findById(String id) {
        return null;
    }

    @Transactional
    @Override
    public AgencySODto update(AgencySODto dto) {
        return null;
    }


    @Transactional
    @Override
    public void delete(String id) {

    }
}
