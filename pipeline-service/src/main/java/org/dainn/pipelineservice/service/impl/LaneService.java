package org.dainn.pipelineservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.pipelineservice.dto.LaneDto;
import org.dainn.pipelineservice.mapper.ILaneMapper;
import org.dainn.pipelineservice.repository.ILaneRepository;
import org.dainn.pipelineservice.service.ILaneService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LaneService implements ILaneService {
    private final ILaneRepository laneRepository;
    private final ILaneMapper laneMapper;

    @Override
    public LaneDto create(LaneDto dto) {
        return null;
    }

    @Override
    public LaneDto findById(String id) {
        return null;
    }

    @Override
    public LaneDto update(LaneDto dto) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
