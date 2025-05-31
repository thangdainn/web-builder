package org.dainn.userservice.repository;

import org.dainn.userservice.model.User;
import org.dainn.userservice.util.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByIdAndRole(String id, Role role);
    Optional<User> findByAgencyIdAndRole(String agencyId, Role role);
    Optional<User> findByIdAndAgencyId(String id, String agencyId);
    List<User> findAllByAgencyId(String agencyId);

    @Query("""
                SELECT u
                FROM User u
                WHERE (:email IS NULL OR u.email LIKE %:email%)
                            AND (:role IS NULL OR u.role = :role)
                            AND (:agencyId IS NULL OR u.agencyId = :agencyId)
            """)
    Page<User> findByFilters(String email, Role role, String agencyId, Pageable pageable);

    @Modifying
    void deleteAllByAgencyId(String agencyId);
    int countByAgencyId(String agencyId);
}
