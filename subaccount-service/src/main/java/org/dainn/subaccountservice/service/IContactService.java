package org.dainn.subaccountservice.service;


import org.dainn.subaccountservice.dto.ContactDto;
import org.dainn.subaccountservice.dto.ContactReq;
import org.springframework.data.domain.Page;

public interface IContactService {
    ContactDto create(ContactDto dto);
    void deleteById(String id);
    ContactDto findById(String id);
    Page<ContactDto> findBySA(ContactReq request);
}
