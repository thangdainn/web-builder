package org.dainn.agencyservice.repository;

import org.dainn.agencyservice.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IAgencyRepository extends JpaRepository<Agency, String> {
    @Modifying
    @Query("""
                UPDATE Agency a
                SET a.goal = :goal
                WHERE a.id = :id
            """)
    void updateGoal(String id, Integer goal);
}
