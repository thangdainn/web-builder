package org.dainn.pipelineservice.repository;

import org.dainn.pipelineservice.model.Lane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILaneRepository extends JpaRepository<Lane, String> {
}
