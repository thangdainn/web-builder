package org.dainn.pipelineservice.repository;

import org.dainn.pipelineservice.model.Lane;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILaneRepository extends JpaRepository<Lane, String> {
    List<Lane> findAllByPipelineId(String pipelineId);
    List<Lane> findAllByPipelineId(String pipelineId, Sort sort);

    int countByPipelineId(String pipelineId);
}
