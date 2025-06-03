package org.dainn.pipelineservice.service;

import org.dainn.pipelineservice.dto.event.TicketOrderEvent;
import org.dainn.pipelineservice.dto.ticket.TicketDto;
import org.dainn.pipelineservice.dto.ticket.TicketOrderList;
import org.dainn.pipelineservice.dto.ticket.UpdateTicketDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ITicketService {
    TicketDto create(TicketDto dto);
    TicketDto findById(String id);
    TicketDto update(String id, UpdateTicketDto dto);
    void delete(String id);
    List<TicketDto> findByLaneId(String laneId, Sort sort);
    void changeOrder(TicketOrderEvent data);
    void changeOrderWithKafka(List<TicketOrderList> list);
    boolean contactIsAssigned(String contactId);
}
