package org.dainn.subaccountservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.subaccountservice.dto.SubAccountDto;
import org.dainn.subaccountservice.mapper.ISubAccountMapper;
import org.dainn.subaccountservice.repository.ISubAccountRepository;
import org.dainn.subaccountservice.service.ISubAccountService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubAccountService implements ISubAccountService {
    private final ISubAccountRepository subAccountRepository;
    private final ISubAccountMapper subAccountMapper;


    @Override
    public SubAccountDto create(SubAccountDto dto) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public void update(SubAccountDto dto) {

    }

    @Override
    public SubAccountDto findById(String id) {
        return null;
    }
}
