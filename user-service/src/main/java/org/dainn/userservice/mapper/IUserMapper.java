package org.dainn.userservice.mapper;

import org.dainn.userservice.dto.user.UserDetailDto;
import org.dainn.userservice.dto.user.UserDto;
import org.dainn.userservice.model.Invitation;
import org.dainn.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    UserDto toDto(User user);
    UserDetailDto toDetail(User user);
    User toEntity(UserDto userDto);
    UserDto toUserInfo(@MappingTarget UserDto userDto, Invitation invitation);
}
