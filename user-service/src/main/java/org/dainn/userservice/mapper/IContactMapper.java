package org.dainn.userservice.mapper;

import org.dainn.userservice.dto.contact.ContactDto;
import org.dainn.userservice.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IContactMapper {
    @Mapping(target = "subAccountId", source = "subAccount.id")
    ContactDto toDto(Contact entity);
    Contact toEntity(ContactDto dto);
}
