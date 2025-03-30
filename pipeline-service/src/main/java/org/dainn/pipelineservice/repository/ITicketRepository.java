package org.dainn.pipelineservice.repository;

import org.dainn.pipelineservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket, String> {
}
