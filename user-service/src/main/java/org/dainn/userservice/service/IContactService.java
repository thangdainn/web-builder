package org.dainn.userservice.service;

import org.dainn.userservice.dto.PageResponse;
import org.dainn.userservice.dto.contact.ContactDto;
import org.dainn.userservice.dto.contact.ContactReq;

public interface IContactService {
    ContactDto create(ContactDto dto);
    void deleteById(String id);
    ContactDto findById(String id);
    PageResponse<ContactDto> findBySA(String subAccountId, ContactReq request);
}
