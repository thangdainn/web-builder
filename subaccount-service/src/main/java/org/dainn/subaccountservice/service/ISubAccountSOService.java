package org.dainn.subaccountservice.service;

import org.dainn.subaccountservice.dto.subaccount.SubAccountSODto;
import org.dainn.subaccountservice.model.SubAccount;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISubAccountSOService {
    List<SubAccountSODto> create(SubAccount subAccount);
    void delete(String id);
    void update(String id);
    SubAccountSODto findById(String id);
    List<SubAccountSODto> findBySA(String saId);
    Page<SubAccountSODto> findAll();
}
