package org.dainn.subaccountservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.subaccountservice.dto.response.PermissionDto;
import org.dainn.subaccountservice.dto.response.PipelineDto;
import org.dainn.subaccountservice.dto.response.TagDto;
import org.dainn.subaccountservice.dto.response.UserDto;
import org.dainn.subaccountservice.dto.subaccount.CreateSubAccount;
import org.dainn.subaccountservice.dto.subaccount.SubAccountDto;
import org.dainn.subaccountservice.dto.subaccount.SubAccountReq;
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

    @Transactional
    @Override
    public SubAccountDto create(CreateSubAccount dto) {
        UserDto userDto = userClient.getByEmail(dto.getUserEmail()).getBody();
        assert userDto != null;
        if (!userDto.getRole().equals("AGENCY_OWNER") || !userDto.getAgencyId().equals(dto.getAgencyId())) {
            throw new AppException(ErrorCode.USER_NOT_AGENCY_OWNER);
        }
        SubAccount entity = subAccountMapper.toEntity(dto);
        entity = subAccountRepository.save(entity);
        SubAccountDto newSA = subAccountMapper.toDto(entity);
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
        return newSA;
    }

    @Transactional
    @Override
    public void deleteBy(String id) {
        subAccountRepository.deleteById(id);
    }

    @Transactional
    @Override
    public SubAccountDto update(SubAccountDto dto) {
        return null;
    }

    @Override
    public SubAccountDto findById(String id) {
        SubAccountDto dto = subAccountMapper.toDto(subAccountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SA_NOT_EXISTED)));
        dto.setOptions(subAccountSOService.findBySA(dto.getId()));
        List<TagDto> tags = tagClient.getBySubAccount(id).getBody();
        dto.setTags(tags);
        return dto;
    }

    @Override
    public Page<SubAccountDto> findAll(SubAccountReq request) {
        Pageable pageable = Paging.getPageable(request);
        if (StringUtils.hasText(request.getKeyword())) {
            return subAccountRepository.findAllByNameContainingIgnoreCase(request.getKeyword(), pageable)
                    .map(subAccountMapper::toDto);
        } else {
            return subAccountRepository.findAll(pageable)
                    .map(subAccountMapper::toDto);
        }
    }
}
