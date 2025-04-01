package org.dainn.subaccountservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.subaccountservice.dto.PermissionDto;
import org.dainn.subaccountservice.dto.SubAccountDto;
import org.dainn.subaccountservice.dto.response.TagDto;
import org.dainn.subaccountservice.exception.AppException;
import org.dainn.subaccountservice.exception.ErrorCode;
import org.dainn.subaccountservice.feignclient.IPermissionClient;
import org.dainn.subaccountservice.feignclient.ITagClient;
import org.dainn.subaccountservice.feignclient.IUserClient;
import org.dainn.subaccountservice.mapper.ISubAccountMapper;
import org.dainn.subaccountservice.model.SubAccount;
import org.dainn.subaccountservice.repository.ISubAccountRepository;
import org.dainn.subaccountservice.service.ISubAccountSOService;
import org.dainn.subaccountservice.service.ISubAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public SubAccountDto create(SubAccountDto dto) {
        SubAccount entity = subAccountMapper.toEntity(dto);
        entity = subAccountRepository.save(entity);
        SubAccountDto newSA = subAccountMapper.toDto(entity);
        newSA.setOptions(subAccountSOService.create(dto.getOptions(), entity.getId()));
//        UserDto user = userClient.getByEmail(dto.getCompanyEmail()).getBody();
        PermissionDto permissionDto = PermissionDto.builder()
                .email(dto.getCompanyEmail())
                .subAccountId(newSA.getId())
                .build();
        permissionClient.create(permissionDto);
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
        List<TagDto> tags = tagClient.getBySubAccount(id).getBody();
        dto.setTags(tags);
        return dto;
    }
}
