package org.dainn.userservice.repository;

import org.dainn.userservice.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, String> {
    Optional<Permission> findByUserIdAndSubAccountId(String userId, String subAccountId);
    List<Permission> findAllByUserId(String userId);

    @Modifying
    void deleteAllBySubAccountId(String subAccountId);
}
