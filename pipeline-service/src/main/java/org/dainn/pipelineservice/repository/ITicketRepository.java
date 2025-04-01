package org.dainn.pipelineservice.repository;

import org.dainn.pipelineservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket, String> {
    List<Ticket> findAllByLaneId(String laneId);
    List<Ticket> findAllByLane_Pipeline_Id(String pipelineId);

    int countByLaneId(String id);
}
