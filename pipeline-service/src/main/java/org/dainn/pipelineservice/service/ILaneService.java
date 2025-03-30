package org.dainn.pipelineservice.service;

import org.dainn.pipelineservice.dto.LaneDto;

public interface ILaneService {
    LaneDto create(LaneDto dto);
    LaneDto findById(String id);
    LaneDto update(LaneDto dto);
    void delete(String id);
}
