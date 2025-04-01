package org.dainn.pipelineservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.pipelineservice.dto.lane.LaneDetailDto;
import org.dainn.pipelineservice.dto.lane.LaneDto;
import org.dainn.pipelineservice.dto.lane.LaneOrderDto;
import org.dainn.pipelineservice.exception.AppException;
import org.dainn.pipelineservice.exception.ErrorCode;
import org.dainn.pipelineservice.mapper.ILaneMapper;
import org.dainn.pipelineservice.model.Lane;
import org.dainn.pipelineservice.repository.ILaneRepository;
import org.dainn.pipelineservice.service.ILaneService;
import org.dainn.pipelineservice.service.IPipelineService;
import org.dainn.pipelineservice.service.ITicketService;
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
    private final IPipelineService pipelineService;
    private final ITicketService ticketService;

    @Transactional
    @Override
    public LaneDto create(LaneDto dto) {
        Lane lane = laneMapper.toEntity(dto);
        return laneMapper.toDto(laneRepository.save(lane));
    }

    @Override
    public LaneDto findById(String id) {
        return laneMapper.toDto(laneRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LANE_NOT_EXISTED)));
    }

    @Transactional
    @Override
    public LaneDto update(LaneDto dto) {
        Lane old = laneRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.LANE_NOT_EXISTED));
        old = laneMapper.toUpdate(old, dto);
        return laneMapper.toDto(laneRepository.save(old));
    }

    @Transactional
    @Override
    public void delete(String id) {
        laneRepository.deleteById(id);
    }

    @Override
    public List<LaneDetailDto> findByPipeline(String id) {
        pipelineService.findById(id);
        return laneRepository.findAllByPipelineId(id)
                .stream().map((lane) -> {
                    LaneDetailDto detailDto = laneMapper.toDetail(lane);
                    detailDto.setTickets(ticketService.findByLaneId(lane.getId()));
                    return detailDto;
                }).toList();
    }

    @Transactional
    @Override
    public void changeOrder(String pipelineId, List<LaneOrderDto> list) {
        List<Lane> lanes = laneRepository.findAllByPipelineId(pipelineId);
        Map<String, Lane> laneMap = lanes.stream().collect(Collectors.toMap(Lane::getId, l -> l));
        list.forEach((item) -> {
            Lane lane = laneMap.get(item.getLaneId());
            lane.setOrder(item.getOrder());
        });
        laneRepository.saveAll(lanes);
    }
}
