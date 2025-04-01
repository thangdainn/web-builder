package org.dainn.subaccountservice.service;


import org.dainn.subaccountservice.dto.SubAccountDto;

public interface ISubAccountService {
    SubAccountDto create(SubAccountDto dto);
    SubAccountDto update(SubAccountDto dto);
    void deleteBy(String id);
    SubAccountDto findById(String id);
}
