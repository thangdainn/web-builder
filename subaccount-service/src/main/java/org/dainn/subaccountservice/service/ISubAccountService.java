package org.dainn.subaccountservice.service;


import org.dainn.subaccountservice.dto.subaccount.CreateSubAccount;
import org.dainn.subaccountservice.dto.subaccount.SubAccountDetailDto;
import org.dainn.subaccountservice.dto.subaccount.SubAccountDto;
import org.dainn.subaccountservice.dto.subaccount.SubAccountReq;
import org.springframework.data.domain.Page;

public interface ISubAccountService {
    SubAccountDetailDto create(CreateSubAccount dto);
    SubAccountDetailDto update(SubAccountDetailDto dto);
    void deleteBy(String id);
    SubAccountDto findById(String id);
    SubAccountDetailDto findDetailById(String id);

    Page<SubAccountDetailDto> findAll(SubAccountReq request);
}
