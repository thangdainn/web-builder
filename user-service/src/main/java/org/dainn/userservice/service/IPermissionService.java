package org.dainn.userservice.service;

import org.dainn.userservice.dto.permission.PermissionDto;

import java.util.List;

public interface IPermissionService {
    PermissionDto create(PermissionDto dto);
    void delete(String id);
    void deleteBySA(String subAccountId);
    void updateAccess(String id, Boolean access);
    List<PermissionDto> findAllByUser(String userId);
}
