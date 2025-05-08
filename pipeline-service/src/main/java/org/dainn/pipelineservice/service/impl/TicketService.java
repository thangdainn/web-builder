package org.dainn.pipelineservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.pipelineservice.dto.ticket.TicketDto;
import org.dainn.pipelineservice.dto.ticket.TicketOrderDto;
import org.dainn.pipelineservice.dto.ticket.TicketOrderList;
import org.dainn.pipelineservice.exception.AppException;
import org.dainn.pipelineservice.exception.ErrorCode;
import org.dainn.pipelineservice.feignclient.IContactClient;
import org.dainn.pipelineservice.feignclient.IUserClient;
import org.dainn.pipelineservice.mapper.ITagMapper;
import org.dainn.pipelineservice.mapper.ITicketMapper;
import org.dainn.pipelineservice.model.Lane;
import org.dainn.pipelineservice.model.Tag;
import org.dainn.pipelineservice.model.Ticket;
import org.dainn.pipelineservice.repository.ILaneRepository;
import org.dainn.pipelineservice.repository.ITagRepository;
import org.dainn.pipelineservice.repository.ITicketRepository;
import org.dainn.pipelineservice.service.ITicketService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {
    private final ITicketRepository ticketRepository;
    private final ITagRepository tagRepository;
    private final ILaneRepository laneRepository;
    private final ITicketMapper ticketMapper;
    private final ITagMapper tagMapper;
    private final IUserClient userClient;
    private final IContactClient contactClient;

    @Transactional
    @Override
    public TicketDto create(TicketDto dto) {
        Lane lane = laneRepository.findById(dto.getLaneId())
                .orElseThrow(() -> new AppException(ErrorCode.LANE_NOT_EXISTED));
        Ticket ticket = ticketMapper.toEntity(dto);
        int count = ticketRepository.countByLaneId(dto.getLaneId());
        ticket.setOrder(count);
        ticket.setLane(lane);
        List<Tag> tags = dto.getTags().stream().map((tag) -> tagRepository.findById(tag.getId())
                .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_EXISTED))).toList();
        ticket.setTags(tags);
        return ticketMapper.toDto(ticketRepository.save(ticket));
    }

    @Override
    public TicketDto findById(String id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_EXISTED));
        return mapToTicketDto(ticket);
    }

    private TicketDto mapToTicketDto(Ticket ticket) {
        TicketDto ticketDto = ticketMapper.toDto(ticket);
        ticketDto.setTags(ticket.getTags()
                .stream().map(tagMapper::toDto).toList());
        if (StringUtils.hasText(ticket.getAssignedUserId())) {
            ticketDto.setAssignedName(Objects.requireNonNull(userClient.getById(ticket.getAssignedUserId())
                    .getBody()).getName());
        }
        if (StringUtils.hasText(ticket.getCustomerId())) {
            ticketDto.setCustomerName(Objects.requireNonNull(contactClient.getById(ticket.getCustomerId())
                    .getBody()).getName());
        }
        return ticketDto;
    }

    @Transactional
    @Override
    public TicketDto update(TicketDto dto) {
        Ticket ticket = ticketRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_EXISTED));
        ticket = ticketMapper.toUpdate(ticket, dto);
        List<Tag> tags = dto.getTags().stream().map((tag) -> tagRepository.findById(tag.getId())
                .orElseThrow(() -> new AppException(ErrorCode.LANE_NOT_EXISTED))).toList();
        ticket.setTags(tags);
        return ticketMapper.toDto(ticketRepository.save(ticket));
    }

    @Transactional
    @Override
    public void delete(String id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public List<TicketDto> findByLaneId(String laneId, Sort sort) {
        List<Ticket> tickets = ticketRepository.findAllByLaneId(laneId, sort);
        return tickets.stream().map(this::mapToTicketDto).toList();
    }

    @Transactional
    @Override
    public void changeOrder(List<TicketOrderList> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("TicketOrderList cannot be empty");
        }
        List<Ticket> tickets = new ArrayList<>();
        for (TicketOrderList ticketOrderList : list) {
            tickets.addAll(updateTicketOrderInLane(ticketOrderList));
        }
        ticketRepository.saveAll(tickets);
    }

    private List<Ticket> updateTicketOrderInLane(TicketOrderList ticketOrderList) {
        String lainId = ticketOrderList.getLaneId();
        Lane lane = laneRepository.findById(lainId)
                .orElseThrow(() -> new AppException(ErrorCode.LANE_NOT_EXISTED));
        List<Ticket> tickets = ticketRepository.findAllByLaneId(lainId);
        Map<String, Ticket> ticketMap = tickets.stream()
                .collect(Collectors.toMap(Ticket::getId, ticket -> ticket));
        List<TicketOrderDto> ticketOrders = ticketOrderList.getTicketOrders();
        for (TicketOrderDto dto : ticketOrders) {
            Ticket ticket = ticketMap.get(dto.getTicketId());
            if (ticket != null) {
                ticket.setOrder(dto.getOrder());
            } else {
                ticket = ticketRepository.findById(dto.getTicketId())
                        .orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_EXISTED));
                ticket.setOrder(dto.getOrder());
                ticket.setLane(lane);
                tickets.add(ticket);
            }
        }
        return tickets;
    }
}
