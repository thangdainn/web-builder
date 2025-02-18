package org.dainn.userservice.mapper;

import org.dainn.userservice.dto.UserDto;
import org.dainn.userservice.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
