package org.dainn.pipelineservice.service;

import org.dainn.pipelineservice.dto.lane.LaneDetailDto;
import org.dainn.pipelineservice.dto.lane.LaneDto;
import org.dainn.pipelineservice.dto.lane.LaneOrderDto;

import java.util.List;

public interface ILaneService {
    LaneDto create(LaneDto dto);
    LaneDto findById(String id);
    LaneDto update(LaneDto dto);
    void delete(String id);
    List<LaneDetailDto> findByPipeline(String id);
    void changeOrder(String pipelineId, List<LaneOrderDto> list);
}
