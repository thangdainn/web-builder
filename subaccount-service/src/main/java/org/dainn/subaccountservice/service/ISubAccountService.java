package org.dainn.subaccountservice.service;


import org.dainn.subaccountservice.dto.subaccount.CreateSubAccount;
import org.dainn.subaccountservice.dto.subaccount.SubAccountDto;
import org.dainn.subaccountservice.dto.subaccount.SubAccountReq;
import org.springframework.data.domain.Page;

public interface ISubAccountService {
    SubAccountDto create(CreateSubAccount dto);
    SubAccountDto update(SubAccountDto dto);
    void deleteBy(String id);
    SubAccountDto findById(String id);

    Page<SubAccountDto> findAll(SubAccountReq request);
}
