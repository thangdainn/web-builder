package org.dainn.agencyservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.agencyservice.dto.*;
import org.dainn.agencyservice.event.EventProducer;
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
    private final EventProducer eventProducer;

    @Transactional
    @Override
    public AgencyDto create(CreateAgencyDto dto) {
        Agency agency = agencyMapper.toEntity(dto);
        agency = agencyRepository.save(agency);
        AgencyDto newAgency = agencyMapper.toDto(agency);
        newAgency.setOptions(agencySOService.create(agency));
        dto.getUser().setAgencyId(agency.getId());
        userClient.setOwner(dto.getUser().getEmail(), dto.getUser());
        eventProducer.sendCreateCustomerEvent(CreateCustomer.builder()
                .email(dto.getCompanyEmail())
                .name(dto.getName())
                .phone(dto.getCompanyPhone())
                .line1(dto.getAddress())
                .city(dto.getCity())
                .state(dto.getState())
                .zipCode(dto.getZipCode())
                .country(dto.getCountry())
                .agencyId(newAgency.getId())
                .build());
        eventProducer.changeAgencyEvent(dto.getUser().getEmail());
        return newAgency;
    }

    @Transactional
    @Override
    public AgencyDto update(AgencyDto dto) {
        Agency old = agencyRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.AGENCY_NOT_EXISTED));
        Agency newAgency = agencyMapper.update(old, dto);
        newAgency = agencyRepository.save(newAgency);
        eventProducer.changeAgencyEvent(dto.getUserEmail());
        return agencyMapper.toDto(newAgency);
    }

    @Transactional
    @Override
    public AgencyDto updateGoal(String id, UpdateGoalDto dto) {
        agencyRepository.updateGoal(id, dto.getGoal());
        return agencyMapper.toDto(agencyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AGENCY_NOT_EXISTED)));
    }

    @Override
    public AgencyDto findById(String id) {
        return agencyMapper.toDto(agencyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AGENCY_NOT_EXISTED)));
    }

    @Override
    public AgencyDetailDto findDetailById(String id) {
        AgencyDetailDto detail = agencyMapper.toDetail(agencyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AGENCY_NOT_EXISTED)));
        detail.setOptions(agencySOService.findByAgency(id));
        detail.setSubscription(subscriptionClient.getByAgencyId(id));
        return detail;
    }

    @Override
    public AgencyDetailDto findByCustomerId(String id) {
        AgencyDetailDto detail = agencyMapper.toDetail(agencyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AGENCY_NOT_EXISTED)));
        detail.setSubscription(subscriptionClient.getByAgencyId(id));
        return detail;
    }

    @Transactional
    @Override
    public void delete(String id) {
        agencyRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateConnectAccId(String id, String connectAccId) {
        agencyRepository.updateConnectAccId(id, connectAccId);
    }
}
