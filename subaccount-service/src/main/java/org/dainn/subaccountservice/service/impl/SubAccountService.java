package org.dainn.subaccountservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.subaccountservice.dto.response.PermissionDto;
import org.dainn.subaccountservice.dto.response.PipelineDto;
import org.dainn.subaccountservice.dto.response.TagDto;
import org.dainn.subaccountservice.dto.response.UserDto;
import org.dainn.subaccountservice.dto.subaccount.*;
import org.dainn.subaccountservice.event.EventProducer;
import org.dainn.subaccountservice.exception.AppException;
import org.dainn.subaccountservice.exception.ErrorCode;
import org.dainn.subaccountservice.feignclient.IPermissionClient;
import org.dainn.subaccountservice.feignclient.IPipelineClient;
import org.dainn.subaccountservice.feignclient.ITagClient;
import org.dainn.subaccountservice.feignclient.IUserClient;
import org.dainn.subaccountservice.mapper.ISubAccountMapper;
import org.dainn.subaccountservice.model.SubAccount;
import org.dainn.subaccountservice.repository.ISubAccountRepository;
import org.dainn.subaccountservice.service.ISubAccountSOService;
import org.dainn.subaccountservice.service.ISubAccountService;
import org.dainn.subaccountservice.util.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubAccountService implements ISubAccountService {
    private final ISubAccountRepository subAccountRepository;
    private final ISubAccountSOService subAccountSOService;
    private final ISubAccountMapper subAccountMapper;
    private final IPermissionClient permissionClient;
    private final ITagClient tagClient;
    private final IUserClient userClient;
    private final IPipelineClient pipelineClient;
    private final EventProducer eventProducer;

    @Transactional
    @Override
    public SubAccountDetailDto create(CreateSubAccount dto) {
        UserDto userDto = userClient.getByEmail(dto.getUserEmail()).getBody();
        assert userDto != null;
        if (!userDto.getRole().equals("AGENCY_OWNER") || !userDto.getAgencyId().equals(dto.getAgencyId())) {
            throw new AppException(ErrorCode.USER_NOT_AGENCY_OWNER);
        }
        SubAccount entity = subAccountMapper.toEntity(dto);
        entity = subAccountRepository.save(entity);
        SubAccountDetailDto newSA = subAccountMapper.toDetailDto(entity);
        newSA.setOptions(subAccountSOService.create(entity));
        PermissionDto permissionDto = PermissionDto.builder()
                .access(true)
                .email(userDto.getEmail())
                .subAccountId(newSA.getId())
                .build();
        permissionClient.create(permissionDto);
        PipelineDto pipelineDto = PipelineDto.builder()
                .subAccountId(newSA.getId())
                .name("Lead Cycle")
                .build();
        pipelineClient.create(pipelineDto);
        eventProducer.changeAgencyEvent(userDto.getEmail());
        eventProducer.changePerEvent(userDto.getEmail());
        return newSA;
    }

    @Transactional
    @Override
    public void delete(String id, String email) {
        subAccountRepository.deleteById(id);
        eventProducer.changePerEvent(email);
        eventProducer.changeAgencyEvent(email);
    }

    @Transactional
    @Override
    public void deleteByAgency(String agencyId) {
        subAccountRepository.deleteByAgencyId(agencyId);
        log.info("Deleted all sub-accounts for agency ID: {}", agencyId);
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
        eventProducer.changeAgencyEvent(dto.getUserEmail());
        eventProducer.changePerEvent(dto.getUserEmail());
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
}
