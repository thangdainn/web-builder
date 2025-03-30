package org.dainn.subaccountservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.dainn.subaccountservice.dto.SubAccountSODto;
import org.dainn.subaccountservice.repository.ISubAccountSORepository;
import org.dainn.subaccountservice.service.ISubAccountSOService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubAccountSOService implements ISubAccountSOService {
    private final ISubAccountSORepository subAccountSORepository;


    @Override
    public SubAccountSODto createUser(SubAccountSODto dto) {
        return null;
    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    public void updateUser(String id) {

    }

    @Override
    public SubAccountSODto findById(String id) {
        return null;
    }

    @Override
    public Page<SubAccountSODto> findAllUsers() {
        return null;
    }
}
