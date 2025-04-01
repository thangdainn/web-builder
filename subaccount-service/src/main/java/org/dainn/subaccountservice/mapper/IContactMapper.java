package org.dainn.subaccountservice.mapper;

import org.dainn.subaccountservice.dto.ContactDto;
import org.dainn.subaccountservice.model.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IContactMapper {
    ContactDto toDto(Contact entity);
    Contact toEntity(ContactDto dto);
}
