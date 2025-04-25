package org.dainn.userservice.service;

import org.dainn.userservice.dto.user.UserDetailDto;
import org.dainn.userservice.dto.user.UserDto;
import org.dainn.userservice.dto.user.UserReq;
import org.springframework.data.domain.Page;

public interface IUserService {
    UserDto create(UserDto dto);
    void delete(String id);
    void deleteByAgency(String agencyId);
    UserDto update(UserDto dto);
    UserDetailDto findDetailById(String id);
    UserDto findById(String id);
    UserDto findByEmail(String email);
    Page<UserDto> findAll(UserReq req);
    boolean isOwner(String id);
    UserDto findOwnerByAgency(String id);
    void setOwner(UserDto dto);
    void syncPermission(String email);
    void syncAgency(String email);
    void syncUser();

    Boolean isOwnerAgency(UserDto dto);
}
