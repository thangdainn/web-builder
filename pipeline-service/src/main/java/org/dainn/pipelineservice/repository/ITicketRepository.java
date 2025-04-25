package org.dainn.pipelineservice.repository;

import org.dainn.pipelineservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket, String> {
    List<Ticket> findAllByLaneId(String laneId);

    int countByLaneId(String id);
}
