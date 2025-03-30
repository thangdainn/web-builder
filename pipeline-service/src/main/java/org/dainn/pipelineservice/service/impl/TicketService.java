package org.dainn.pipelineservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.pipelineservice.dto.LaneDto;
import org.dainn.pipelineservice.dto.TicketDto;
import org.dainn.pipelineservice.mapper.ILaneMapper;
import org.dainn.pipelineservice.mapper.ITicketMapper;
import org.dainn.pipelineservice.repository.ILaneRepository;
import org.dainn.pipelineservice.repository.ITicketRepository;
import org.dainn.pipelineservice.service.ILaneService;
import org.dainn.pipelineservice.service.ITicketService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {
    private final ITicketRepository ticketRepository;
    private final ITicketMapper laneMapper;


    @Override
    public TicketDto create(TicketDto dto) {
        return null;
    }

    @Override
    public TicketDto findById(String id) {
        return null;
    }

    @Override
    public TicketDto update(TicketDto dto) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
