package org.dainn.pipelineservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.pipelineservice.dto.PipelineDto;
import org.dainn.pipelineservice.mapper.IPipelineMapper;
import org.dainn.pipelineservice.repository.IPipelineRepository;
import org.dainn.pipelineservice.service.IPipelineService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PipelineService implements IPipelineService {
    private final IPipelineRepository pipelineRepository;
    private final IPipelineMapper pipelineMapper;

    @Override
    public PipelineDto create(PipelineDto dto) {
        return null;
    }

    @Override
    public PipelineDto findById(String id) {
        return null;
    }

    @Override
    public PipelineDto update(PipelineDto dto) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
