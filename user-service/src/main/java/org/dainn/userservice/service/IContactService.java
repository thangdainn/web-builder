package org.dainn.userservice.service;

import org.dainn.userservice.dto.contact.ContactDto;
import org.dainn.userservice.dto.contact.ContactReq;
import org.springframework.data.domain.Page;

public interface IContactService {
    ContactDto create(ContactDto dto);
    void deleteById(String id);
    ContactDto findById(String id);
    Page<ContactDto> findBySA(String subAccountId, ContactReq request);
}
