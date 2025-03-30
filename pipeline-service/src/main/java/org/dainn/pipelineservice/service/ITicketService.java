package org.dainn.pipelineservice.service;

import org.dainn.pipelineservice.dto.TicketDto;

public interface ITicketService {
    TicketDto create(TicketDto dto);
    TicketDto findById(String id);
    TicketDto update(TicketDto dto);
    void delete(String id);
}
