package org.dainn.subaccountservice.service;

import org.dainn.subaccountservice.dto.SubAccountSODto;
import org.springframework.data.domain.Page;

public interface ISubAccountSOService {
    SubAccountSODto createUser(SubAccountSODto dto);
    void deleteUser(String id);
    void updateUser(String id);
    SubAccountSODto findById(String id);
    Page<SubAccountSODto> findAllUsers();
}
