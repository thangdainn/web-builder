package org.dainn.userservice.service;

import org.dainn.userservice.dto.UserDto;
import org.dainn.userservice.model.User;
import org.springframework.data.domain.Page;

public interface IUserService {
    UserDto createUser(UserDto dto);
    void deleteUser(String id);
    void updateUser(String id);
    UserDto findById(String id);
    Page<User> findAllUsers();
}
