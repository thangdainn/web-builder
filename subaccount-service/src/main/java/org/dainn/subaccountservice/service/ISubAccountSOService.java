package org.dainn.subaccountservice.service;

import org.dainn.subaccountservice.dto.SubAccountSODto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISubAccountSOService {
    List<SubAccountSODto> create(List<SubAccountSODto> list, String subAccountId);
    void deleteUser(String id);
    void updateUser(String id);
    SubAccountSODto findById(String id);
    Page<SubAccountSODto> findAllUsers();
}
