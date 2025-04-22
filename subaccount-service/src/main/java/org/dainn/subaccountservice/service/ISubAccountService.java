package org.dainn.subaccountservice.service;


import org.dainn.subaccountservice.dto.subaccount.CreateSubAccount;
import org.dainn.subaccountservice.dto.subaccount.SubAccountDetailDto;
import org.dainn.subaccountservice.dto.subaccount.SubAccountDto;
import org.dainn.subaccountservice.dto.subaccount.SubAccountReq;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISubAccountService {
    SubAccountDetailDto create(CreateSubAccount dto);
    SubAccountDetailDto update(SubAccountDetailDto dto);
    void delete(String id, String email);
    SubAccountDto findById(String id);
    SubAccountDetailDto findDetailById(String id);

    Page<SubAccountDetailDto> findAll(SubAccountReq request);
    void updateConnectAccId(String id, String connectAccId);

    List<SubAccountDetailDto> findByAgencyId(String id);
}
