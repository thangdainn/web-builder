package org.dainn.pipelineservice.repository;

import org.dainn.pipelineservice.model.Pipeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPipelineRepository extends JpaRepository<Pipeline, String> {
    List<Pipeline> findAllBySubAccountId(String subAccountId);
}
