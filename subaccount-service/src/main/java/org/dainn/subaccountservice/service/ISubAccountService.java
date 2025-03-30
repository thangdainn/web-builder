package org.dainn.subaccountservice.service;


import org.dainn.subaccountservice.dto.SubAccountDto;

public interface ISubAccountService {
    SubAccountDto create(SubAccountDto dto);
    void deleteById(String id);
    void update(SubAccountDto dto);
    SubAccountDto findById(String id);
}
