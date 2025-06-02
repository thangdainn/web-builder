package org.dainn.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.userservice.dto.event.DeleteAgencyEvent;
import org.dainn.userservice.dto.permission.PermissionDto;
import org.dainn.userservice.dto.response.PipelineDto;
import org.dainn.userservice.dto.response.SubscriptionDto;
import org.dainn.userservice.dto.response.TagDto;
import org.dainn.userservice.dto.subaccount.*;
import org.dainn.userservice.event.EventProducer;
import org.dainn.userservice.exception.AppException;
import org.dainn.userservice.exception.ErrorCode;
import org.dainn.userservice.feignclient.IPipelineClient;
import org.dainn.userservice.feignclient.ISubscriptionClient;
import org.dainn.userservice.feignclient.ITagClient;
import org.dainn.userservice.mapper.ISubAccountMapper;
import org.dainn.userservice.model.SubAccount;
import org.dainn.userservice.model.User;
import org.dainn.userservice.repository.ISubAccountRepository;
import org.dainn.userservice.repository.IUserRepository;
import org.dainn.userservice.service.IPermissionService;
import org.dainn.userservice.service.ISubAccountSOService;
import org.dainn.userservice.service.ISubAccountService;
import org.dainn.userservice.util.Paging;
import org.dainn.userservice.util.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubAccountService implements ISubAccountService {
    private final ISubAccountRepository subAccountRepository;
    private final ISubAccountSOService subAccountSOService;
    private final IUserRepository userRepository;
    private final IPermissionService permissionService;
    private final ISubAccountMapper subAccountMapper;
    private final ITagClient tagClient;
    private final IPipelineClient pipelineClient;
    private final EventProducer eventProducer;
    private final ISubscriptionClient subscriptionClient;

    @Transactional
    @Override
    public SubAccountDetailDto create(CreateSubAccount dto) {
        User user = userRepository.findByEmail(dto.getUserEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (!user.getRole().equals(Role.AGENCY_OWNER) || !user.getAgencyId().equals(dto.getAgencyId())) {
            throw new AppException(ErrorCode.USER_NOT_AGENCY_OWNER);
        }
        SubscriptionDto subscriptionDto = subscriptionClient.getByAgencyId(dto.getAgencyId()).getBody();
        if (subscriptionDto == null || !subscriptionDto.getActive() ) {
            if (subAccountRepository.countByAgencyId(dto.getAgencyId()) >= 3) {
                throw new AppException(ErrorCode.SA_LIMIT);
            }
        }

        SubAccount entity = subAccountMapper.toEntity(dto);
        entity = subAccountRepository.save(entity);
        SubAccountDetailDto newSA = subAccountMapper.toDetailDto(entity);
        newSA.setOptions(subAccountSOService.create(entity));
        PermissionDto permissionDto = PermissionDto.builder()
                .access(true)
                .email(user.getEmail())
                .subAccountId(newSA.getId())
                .build();
        permissionService.create(permissionDto);
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
//            @Override
//            public void afterCommit() {
//                eventProducer.changeAgencyEvent(user.getEmail());
//                eventProducer.changePerEvent(user.getEmail());
//            }
//        });
        afterCommitEvent(user.getEmail());

//        eventProducer.changeAgencyEvent(user.getEmail());
//        eventProducer.changePerEvent(user.getEmail());
        PipelineDto pipelineDto = PipelineDto.builder()
                .subAccountId(newSA.getId())
                .name("Lead Cycle")
                .build();
        pipelineClient.create(pipelineDto);
        return newSA;
    }

    @Transactional
    @Override
    public void delete(String id, String email) {
        subAccountRepository.deleteById(id);
        eventProducer.subAccountDeletedEvent(id);
//        eventProducer.changePerEvent(email);
//        eventProducer.changeAgencyEvent(email);
        afterCommitEvent(email);
    }

    @Transactional
    @Override
    public void deleteByAgency(DeleteAgencyEvent dto) {
        List<SubAccount> subAccounts = subAccountRepository.findAllByAgencyId(dto.getAgencyId());
        subAccounts.forEach(subAccount -> {
            eventProducer.subAccountDeletedEvent(subAccount.getId());
        });
        subAccountRepository.deleteAll(subAccounts);
//        subAccountRepository.deleteByAgencyId(dto.getAgencyId());
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
//            @Override
//            public void afterCommit() {
//                eventProducer.changeAgencyEvent(dto.getEmail());
//                eventProducer.changePerEvent(dto.getEmail());
//            }
//        });
        afterCommitEvent(dto.getEmail());

        log.info("Deleted all sub-accounts for agency ID: {}", dto.getAgencyId());
    }

    @Transactional
    @Override
    public SubAccountDetailDto update(String id, UpdateSubAccount dto) {
        SubAccount entity = subAccountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SA_NOT_EXISTED));
        entity = subAccountMapper.toUpdate(entity, dto);
        entity = subAccountRepository.save(entity);
        SubAccountDetailDto updatedSA = subAccountMapper.toDetailDto(entity);
        updatedSA.setOptions(subAccountSOService.findBySA(entity.getId()));
//        eventProducer.changeAgencyEvent(dto.getUserEmail());
//        eventProducer.changePerEvent(dto.getUserEmail());
        afterCommitEvent(dto.getUserEmail());
        return updatedSA;
    }

    @Override
    public SubAccountDto findById(String id) {
        return subAccountMapper.toDto(subAccountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SA_NOT_EXISTED)));
    }

    @Override
    public SubAccountDetailDto findDetailById(String id) {
        SubAccountDetailDto dto = subAccountMapper.toDetailDto(subAccountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SA_NOT_EXISTED)));
        dto.setOptions(subAccountSOService.findBySA(dto.getId()));
        List<TagDto> tags = tagClient.getBySubAccount(id).getBody();
        dto.setTags(tags);
        return dto;
    }

    @Override
    public Page<SubAccountDetailDto> findAll(SubAccountReq request) {
        Pageable pageable = Paging.getPageable(request);
        if (StringUtils.hasText(request.getKeyword())) {
            return subAccountRepository.findAllByNameContainingIgnoreCase(request.getKeyword(), pageable)
                    .map(subAccountMapper::toDetailDto);
        } else {
            return subAccountRepository.findAll(pageable)
                    .map(subAccountMapper::toDetailDto);
        }
    }

    @Transactional
    @Override
    public void updateConnectAccId(String id, String connectAccId) {
        subAccountRepository.updateConnectAccId(id, connectAccId);
    }

    @Override
    public List<SubAccountDetailDto> findByAgencyId(String id) {
        List<SubAccount> subAccounts = subAccountRepository.findAllByAgencyId(id);
        return subAccounts.stream()
                .map(sa -> {
                    SubAccountDetailDto detail = subAccountMapper.toDetailDto(sa);
                    detail.setOptions(subAccountSOService.findBySA(detail.getId()));
                    return detail;
                })
                .toList();
    }

    void afterCommitEvent(String email) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                eventProducer.changeAgencyEvent(email);
                eventProducer.changePerEvent(email);
            }
        });
    }
}
