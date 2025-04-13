package org.dainn.agencyservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.agencyservice.dto.AgencyDetailDto;
import org.dainn.agencyservice.dto.AgencyDto;
import org.dainn.agencyservice.dto.UpdateGoalDto;
import org.dainn.agencyservice.dto.response.UserOwnerDto;
import org.dainn.agencyservice.exception.AppException;
import org.dainn.agencyservice.exception.ErrorCode;
import org.dainn.agencyservice.feignclient.ISubscriptionClient;
import org.dainn.agencyservice.feignclient.IUserClient;
import org.dainn.agencyservice.mapper.IAgencyMapper;
import org.dainn.agencyservice.model.Agency;
import org.dainn.agencyservice.repository.IAgencyRepository;
import org.dainn.agencyservice.service.IAgencySOService;
import org.dainn.agencyservice.service.IAgencyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AgencyService implements IAgencyService {
    private final IAgencyRepository agencyRepository;
    private final IAgencyMapper agencyMapper;
    private final ISubscriptionClient subscriptionClient;
    private final IAgencySOService agencySOService;
    private final IUserClient userClient;

    @Transactional
    @Override
    public AgencyDto create(AgencyDto dto) {
        Agency agency = agencyMapper.toEntity(dto);
        agency = agencyRepository.save(agency);
        AgencyDto newAgency = agencyMapper.toDto(agency);
        newAgency.setOptions(agencySOService.create(agency));
        userClient.setOwner(dto.getCompanyEmail(), new UserOwnerDto(dto.getCompanyEmail(), newAgency.getId()));
        return newAgency;
    }

    @Transactional
    @Override
    public AgencyDto update(AgencyDto dto) {
        Agency old = agencyRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.AGENCY_NOT_EXISTED));
        Agency newAgency = agencyMapper.update(old, dto);
        return agencyMapper.toDto(agencyRepository.save(newAgency));
    }

    @Transactional
    @Override
    public AgencyDto updateGoal(String id, UpdateGoalDto dto) {
        agencyRepository.updateGoal(id, dto.getGoal());
        return agencyMapper.toDto(agencyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AGENCY_NOT_EXISTED)));
    }

    @Override
    public AgencyDetailDto findById(String id) {
        AgencyDetailDto detail = agencyMapper.toDetail(agencyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AGENCY_NOT_EXISTED)));
        detail.setOptions(agencySOService.findByAgency(id));
        detail.setSubscriptions(subscriptionClient.getByAgencyId(id));
        return detail;
    }

    @Transactional
    @Override
    public void delete(String id) {
        agencyRepository.deleteById(id);
    }
}
