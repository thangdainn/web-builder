package org.dainn.agencyservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.agencyservice.dto.*;
import org.dainn.agencyservice.event.EventProducer;
import org.dainn.agencyservice.exception.AppException;
import org.dainn.agencyservice.exception.ErrorCode;
import org.dainn.agencyservice.feignclient.ISubscriptionClient;
import org.dainn.agencyservice.feignclient.IUserClient;
import org.dainn.agencyservice.mapper.IAgencyMapper;
import org.dainn.agencyservice.model.Agency;
import org.dainn.agencyservice.repository.IAgencyRepository;
import org.dainn.agencyservice.repository.IAgencySORepository;
import org.dainn.agencyservice.service.IAgencySOService;
import org.dainn.agencyservice.service.IAgencyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgencyService implements IAgencyService {
    private final IAgencyRepository agencyRepository;
    private final IAgencySORepository agencySORepository;
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
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                eventProducer.changeAgencyEvent(dto.getUser().getEmail());
            }
        });

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
        AgencyDetailDto detail = agencyMapper.toDetail(agencyRepository.findByCustomerId(id)
                .orElseThrow(() -> new AppException(ErrorCode.AGENCY_NOT_EXISTED)));
        detail.setSubscription(subscriptionClient.getByAgencyId(detail.getId()));
        return detail;
    }

    @Transactional
    @Override
    public void delete(DeleteAgencyDto dto) {
//        String userId = "ssssssss";
//        if (Boolean.FALSE.equals(userClient.isOwner(UserDto.builder()
//                .agencyId(id)
//                .id(userId)
//                .build()).getBody())) {
//            throw new AppException(ErrorCode.USER_NOT_PERMISSION);
//        }
        agencySORepository.deleteAllByAgencyId(dto.getAgencyId());
        agencyRepository.deleteById(dto.getAgencyId());
        eventProducer.agencyDeletedEvent(dto);
        log.info("Deleted agency with ID: {}", dto.getAgencyId());
    }

    @Transactional
    @Override
    public void updateConnectAccId(String id, String connectAccId) {
        agencyRepository.updateConnectAccId(id, connectAccId);
    }

    @Transactional
    @Override
    public void updateCustomerId(String id, String customerId) {
        agencyRepository.updateCustomerId(id, customerId);
    }
}
