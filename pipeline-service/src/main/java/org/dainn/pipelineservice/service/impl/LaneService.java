package org.dainn.pipelineservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.pipelineservice.dto.event.LaneOrderEvent;
import org.dainn.pipelineservice.dto.lane.LaneDetailDto;
import org.dainn.pipelineservice.dto.lane.LaneDto;
import org.dainn.pipelineservice.dto.lane.LaneOrderDto;
import org.dainn.pipelineservice.event.EventProducer;
import org.dainn.pipelineservice.exception.AppException;
import org.dainn.pipelineservice.exception.ErrorCode;
import org.dainn.pipelineservice.mapper.ILaneMapper;
import org.dainn.pipelineservice.model.Lane;
import org.dainn.pipelineservice.model.Pipeline;
import org.dainn.pipelineservice.repository.ILaneRepository;
import org.dainn.pipelineservice.repository.IPipelineRepository;
import org.dainn.pipelineservice.service.ILaneService;
import org.dainn.pipelineservice.service.ITicketService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LaneService implements ILaneService {
    private final ILaneRepository laneRepository;
    private final ILaneMapper laneMapper;
    private final IPipelineRepository pipelineRepository;
    private final ITicketService ticketService;
    private final EventProducer eventProducer;

    @Transactional
    @Override
    @CachePut(value = "lanes", key = "#result.id")
    public LaneDto create(LaneDto dto) {
        Pipeline pipeline = pipelineRepository.findById(dto.getPipelineId())
                .orElseThrow(() -> new AppException(ErrorCode.PIPELINE_NOT_EXISTED));
        Lane lane = laneMapper.toEntity(dto);
        int count = laneRepository.countByPipelineId(dto.getPipelineId());
        lane.setOrder(count);
        lane.setPipeline(pipeline);
        return laneMapper.toDto(laneRepository.save(lane));
    }

    @Override
    @Cacheable(value = "lanes", key = "#id")
    public LaneDto findById(String id) {
        return laneMapper.toDto(laneRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LANE_NOT_EXISTED)));
    }

    @Transactional
    @Override
    @CachePut(value = "lanes", key = "#result.id")
    public LaneDto update(LaneDto dto) {
        Lane old = laneRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.LANE_NOT_EXISTED));
        old = laneMapper.toUpdate(old, dto);
        return laneMapper.toDto(laneRepository.save(old));
    }

    @Transactional
    @Override
    @CacheEvict(value = "lanes", key = "#id")
    public void delete(String id) {
        laneRepository.deleteById(id);
    }

    @Override
    public List<LaneDetailDto> findByPipeline(String id) {
        Sort sort = Sort.by("order").ascending();
        return laneRepository.findAllByPipelineId(id, sort)
                .stream().map((lane) -> {
                    LaneDetailDto detailDto = laneMapper.toDetail(lane);
                    detailDto.setTickets(ticketService.findByLaneId(lane.getId(), sort));
                    return detailDto;
                }).toList();
    }

    @Override
    public void changeOrderWithKafka(String pipelineId, List<LaneOrderDto> list) {
        LaneOrderEvent data = LaneOrderEvent.builder()
                .pipelineId(pipelineId)
                .list(list)
                .build();
        eventProducer.changeLaneOrderEvent(data);
    }

    @Transactional
    @Override
    @CacheEvict(value = "lanes", allEntries = true)
    public void changeOrder(LaneOrderEvent data) {
        List<Lane> lanes = laneRepository.findAllByPipelineId(data.getPipelineId());
        Map<String, Lane> laneMap = lanes.stream().collect(Collectors.toMap(Lane::getId, l -> l));
        data.getList().forEach((item) -> {
            Lane lane = laneMap.get(item.getLaneId());
            lane.setOrder(item.getOrder());
        });
        laneRepository.saveAll(lanes);
    }
}
