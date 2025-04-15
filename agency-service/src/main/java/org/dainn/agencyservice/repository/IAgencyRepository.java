package org.dainn.agencyservice.repository;

import org.dainn.agencyservice.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAgencyRepository extends JpaRepository<Agency, String> {
    @Modifying
    @Query("""
                UPDATE Agency a
                SET a.goal = :goal
                WHERE a.id = :id
            """)
    void updateGoal(String id, Integer goal);

    @Modifying
    @Query("""
                UPDATE Agency a
                SET a.connectAccountId = :connectAccId
                WHERE a.id = :id
            """)
    void updateConnectAccId(String id, String connectAccId);

    @Modifying
    @Query("""
                UPDATE Agency a
                SET a.customerId = :customerId
                WHERE a.id = :id
            """)
    void updateCustomerId(String id, String customerId);

    Optional<Agency> findByCustomerId(String customerId);
}
