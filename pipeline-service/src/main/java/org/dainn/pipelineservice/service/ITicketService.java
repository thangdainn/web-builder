package org.dainn.pipelineservice.service;

import org.dainn.pipelineservice.dto.lane.LaneOrderDto;
import org.dainn.pipelineservice.dto.ticket.TicketDto;
import org.dainn.pipelineservice.dto.ticket.TicketOrderDto;
import org.dainn.pipelineservice.dto.ticket.TicketOrderList;

import java.util.List;

public interface ITicketService {
    TicketDto create(TicketDto dto);
    TicketDto findById(String id);
    TicketDto update(TicketDto dto);
    void delete(String id);
    List<TicketDto> findByLaneId(String laneId);
    void changeOrder(List<TicketOrderList> list);
}
