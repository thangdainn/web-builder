package org.dainn.subaccountservice.mapper;

import org.dainn.subaccountservice.dto.contact.ContactDto;
import org.dainn.subaccountservice.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IContactMapper {
    @Mapping(target = "subAccountId", source = "subAccount.id")
    ContactDto toDto(Contact entity);
    Contact toEntity(ContactDto dto);
}
