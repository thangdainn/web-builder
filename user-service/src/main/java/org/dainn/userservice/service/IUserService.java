package org.dainn.userservice.service;

import org.dainn.userservice.dto.user.UserDetailDto;
import org.dainn.userservice.dto.user.UserDto;
import org.dainn.userservice.dto.user.UserOwnerDto;
import org.dainn.userservice.dto.user.UserReq;
import org.springframework.data.domain.Page;

public interface IUserService {
    UserDto create(UserDto dto);
    void delete(String id);
    UserDto update(UserDto dto);
    UserDetailDto findDetailById(String id);
    UserDto findById(String id);
    UserDto findByEmail(String email);
    Page<UserDto> findAll(UserReq req);
    boolean isOwner(String id);
    UserDto findOwnerByAgency(String id);
    void setOwner(UserOwnerDto dto);
}
