package org.dainn.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.userservice.dto.permission.PermissionDto;
import org.dainn.userservice.exception.AppException;
import org.dainn.userservice.exception.ErrorCode;
import org.dainn.userservice.mapper.IPermissionMapper;
import org.dainn.userservice.mapper.IUserMapper;
import org.dainn.userservice.model.Permission;
import org.dainn.userservice.model.User;
import org.dainn.userservice.repository.IPermissionRepository;
import org.dainn.userservice.repository.IUserRepository;
import org.dainn.userservice.service.IPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {
    private final IUserRepository userRepository;
    private final IPermissionRepository permissionRepository;
    private final IUserMapper userMapper;
    private final IPermissionMapper permissionMapper;

    @Transactional
    @Override
    public PermissionDto create(PermissionDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        permissionRepository.findByUserIdAndSubAccountId(user.getId(), dto.getSubAccountId())
                .ifPresent(permission -> {
                    throw new AppException(ErrorCode.PERMISSION_EXISTED);
                });
        Permission permission = permissionMapper.toEntity(dto);
        permission.setUser(user);
        return permissionMapper.toDto(permissionRepository.save(permission));
    }

    @Transactional
    @Override
    public void delete(String id) {
        permissionRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateAccess(String id, Boolean access) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        permission.setAccess(access);
    }

    @Override
    public List<PermissionDto> findAllByUser(String userId) {
        return permissionRepository.findAllByUserId(userId)
                .stream().map(permissionMapper::toDto).toList();
    }
}
