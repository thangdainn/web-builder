package org.dainn.userservice.service;

import org.dainn.userservice.dto.PageResponse;
import org.dainn.userservice.dto.event.DeleteAgencyEvent;
import org.dainn.userservice.dto.subaccount.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISubAccountService {
    SubAccountDetailDto create(CreateSubAccount dto);
    SubAccountDetailDto update(String id, UpdateSubAccount dto);
    void delete(String id, String email);
    void deleteByAgency(DeleteAgencyEvent dto);
    SubAccountDto findById(String id);
    SubAccountDetailDto findDetailById(String id);

    PageResponse<SubAccountDetailDto> findAll(SubAccountReq request);
    void updateConnectAccId(String id, String connectAccId);

    List<SubAccountDetailDto> findByAgencyId(String id);
}
