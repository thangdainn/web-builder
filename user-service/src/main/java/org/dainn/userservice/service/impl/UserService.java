package org.dainn.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.userservice.dto.permission.PermissionDto;
import org.dainn.userservice.dto.user.UserDetailDto;
import org.dainn.userservice.dto.user.UserDto;
import org.dainn.userservice.dto.user.UserReq;
import org.dainn.userservice.exception.AppException;
import org.dainn.userservice.exception.ErrorCode;
import org.dainn.userservice.mapper.IPermissionMapper;
import org.dainn.userservice.mapper.IUserMapper;
import org.dainn.userservice.model.User;
import org.dainn.userservice.repository.IPermissionRepository;
import org.dainn.userservice.repository.IUserRepository;
import org.dainn.userservice.service.IUserService;
import org.dainn.userservice.util.Paging;
import org.dainn.userservice.util.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IPermissionRepository permissionRepository;
    private final IUserMapper userMapper;
    private final IPermissionMapper permissionMapper;

    @Transactional
    @Override
    public UserDto create(UserDto dto) {
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USER_EXISTED);
                });
        User user = userMapper.toEntity(dto);
        User newUser = userRepository.save(user);
        log.info("Created new user: {}", newUser);

        return userMapper.toDto(newUser);
    }

    @Transactional
    @Override
    public void delete(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        permissionRepository.deleteById(id);
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public UserDto update(UserDto dto) {
        User old = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        old = userMapper.toUpdate(old, dto);
        old = userRepository.save(old);
        log.info("Updated user: {}", old);
        return userMapper.toDto(old);
    }

    @Override
    public UserDetailDto findDetailById(String id) {
        UserDetailDto detail = userMapper.toDetail(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        List<PermissionDto> permissions = permissionRepository.findAllByUserId(id)
                .stream().map(permissionMapper::toDto).toList();
        detail.setPermissions(permissions);
        return detail;
    }

    @Override
    public UserDto findById(String id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public UserDto findByEmail(String email) {
        return userMapper.toDto(userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public Page<UserDto> findAll(UserReq req) {
        return userRepository.findByFilters(req.getEmail(), req.getRole(), req.getAgencyId(), Paging.getPageable(req))
                .map(userMapper::toDto);
    }

    @Override
    public boolean isOwner(String id) {
        return userRepository.findByIdAndRole(id, Role.AGENCY_OWNER).isPresent();
    }

    @Override
    public UserDto findOwnerByAgency(String id) {
        return userMapper.toDto(userRepository.findByAgencyIdAndRole(id, Role.AGENCY_OWNER)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Transactional
    @Override
    public void setOwner(UserDto dto) {
        Optional<User> optional = userRepository.findByEmail(dto.getEmail());
        User user = new User();
        if (optional.isPresent()) {
            user = optional.get();
            user = userMapper.toUpdate(user, dto);
        } else {
            user = userMapper.toEntity(dto);
        }
        user.setRole(Role.AGENCY_OWNER);
        user.setAgencyId(dto.getAgencyId());
        log.info("Set owner for user: {}", user);
        userRepository.save(user);
    }
}
