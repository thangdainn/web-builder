package org.dainn.subaccountservice.service;


import org.dainn.subaccountservice.dto.subaccount.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISubAccountService {
    SubAccountDetailDto create(CreateSubAccount dto);
    SubAccountDetailDto update(String id, UpdateSubAccount dto);
    void delete(String id, String email);
    void deleteByAgency(String agencyId);
    SubAccountDto findById(String id);
    SubAccountDetailDto findDetailById(String id);

    Page<SubAccountDetailDto> findAll(SubAccountReq request);
    void updateConnectAccId(String id, String connectAccId);

    List<SubAccountDetailDto> findByAgencyId(String id);
}
