package org.dainn.subaccountservice.repository;

import org.dainn.subaccountservice.model.SubAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISubAccountRepository extends JpaRepository<SubAccount, String> {
    Page<SubAccount> findAllByNameContainingIgnoreCase(String keyword, Pageable pageable);
    List<SubAccount> findAllByAgencyId(String agencyId);

    @Modifying
    @Query("""
                UPDATE SubAccount a
                SET a.connectAccountId = :connectAccId
                WHERE a.id = :id
            """)
    void updateConnectAccId(String id, String connectAccId);

    @Modifying
    void deleteByAgencyId(String agencyId);

    int countByAgencyId(String agencyId);
}
