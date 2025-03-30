package org.dainn.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.userservice.dto.UserDto;
import org.dainn.userservice.mapper.IUserMapper;
import org.dainn.userservice.model.User;
import org.dainn.userservice.repository.IUserRepository;
import org.dainn.userservice.service.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;

    @Override
    public UserDto createUser(UserDto dto) {
        User user = userMapper.toEntity(dto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    public void updateUser(String id) {

    }

    @Override
    public UserDto findById(String id) {
        return userMapper.toDto(userRepository.findById(id).orElse(null));
    }

    @Override
    public Page<User> findAllUsers() {
        return null;
    }
}
