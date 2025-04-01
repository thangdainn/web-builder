package org.dainn.subaccountservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.subaccountservice.dto.SubAccountSODto;
import org.dainn.subaccountservice.exception.AppException;
import org.dainn.subaccountservice.exception.ErrorCode;
import org.dainn.subaccountservice.mapper.ISubAccountSOMapper;
import org.dainn.subaccountservice.model.SubAccount;
import org.dainn.subaccountservice.model.SubAccountSidebarOption;
import org.dainn.subaccountservice.repository.ISubAccountRepository;
import org.dainn.subaccountservice.repository.ISubAccountSORepository;
import org.dainn.subaccountservice.service.ISubAccountSOService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubAccountSOService implements ISubAccountSOService {
    private final ISubAccountSORepository subAccountSORepository;
    private final ISubAccountRepository subAccountRepository;
    private final ISubAccountSOMapper subAccountSOMapper;


    @Transactional
    @Override
    public List<SubAccountSODto> create(List<SubAccountSODto> list, String subAccountId) {
        SubAccount sa = subAccountRepository.findById(subAccountId)
                .orElseThrow(() -> new AppException(ErrorCode.SA_NOT_EXISTED));
        List<SubAccountSidebarOption> options = list.stream().map(dto -> {
            SubAccountSidebarOption option = subAccountSOMapper.toEntity(dto);
            option.setSubAccount(sa);
            return option;
        }).toList();
        options = subAccountSORepository.saveAll(options);
        return options.stream().map(subAccountSOMapper::toDto).toList();
    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    public void updateUser(String id) {

    }

    @Override
    public SubAccountSODto findById(String id) {
        return null;
    }

    @Override
    public Page<SubAccountSODto> findAllUsers() {
        return null;
    }
}
