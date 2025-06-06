package org.dainn.pipelineservice.repository;

import org.dainn.pipelineservice.model.Pipeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPipelineRepository extends JpaRepository<Pipeline, String> {
    List<Pipeline> findAllBySubAccountId(String subAccountId);

    @Modifying
    void deleteAllBySubAccountId(String subAccountId);

    @Modifying
    @Query("""
                UPDATE Pipeline
                SET name = :name
                WHERE id = :id
            
            """)
    void updateNameById(String id, String name);
}
