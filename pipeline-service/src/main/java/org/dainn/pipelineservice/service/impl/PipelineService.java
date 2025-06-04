package org.dainn.pipelineservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.pipelineservice.dto.pipeline.PipelineDto;
import org.dainn.pipelineservice.dto.pipeline.UpdatePipelineDto;
import org.dainn.pipelineservice.exception.AppException;
import org.dainn.pipelineservice.exception.ErrorCode;
import org.dainn.pipelineservice.mapper.IPipelineMapper;
import org.dainn.pipelineservice.model.Pipeline;
import org.dainn.pipelineservice.repository.IPipelineRepository;
import org.dainn.pipelineservice.service.IPipelineService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PipelineService implements IPipelineService {
    private final IPipelineRepository pipelineRepository;
    private final IPipelineMapper pipelineMapper;

    @Transactional
    @Override
    @CachePut(value = "pipelines", key = "#result.id")
    public PipelineDto create(PipelineDto dto) {
        Pipeline pipeline = pipelineMapper.toEntity(dto);
        return pipelineMapper.toDto(pipelineRepository.save(pipeline));
    }

    @Override
    @Cacheable(value = "pipelines", key = "#id")
    public PipelineDto findById(String id) {
        return pipelineMapper.toDto(pipelineRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PIPELINE_NOT_EXISTED)));
    }

    @Transactional
    @Override
    @CachePut(value = "pipelines", key = "#result.id")
    public PipelineDto update(PipelineDto dto) {
        Pipeline pipeline = pipelineRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.PIPELINE_NOT_EXISTED));
        pipeline = pipelineMapper.toUpdate(pipeline, dto);
        return pipelineMapper.toDto(pipelineRepository.save(pipeline));
    }

    @Transactional
    @Override
    @CachePut(value = "pipelines", key = "#id")
    public void updateName(String id, UpdatePipelineDto dto) {
        pipelineRepository.updateNameById(id, dto.getName());
    }

    @Transactional
    @Override
    @CacheEvict(value = "pipelines", key = "#id")
    public void delete(String id) {
        pipelineRepository.deleteById(id);
    }

    @Override
    public List<PipelineDto> findBySA(String id) {
        return pipelineRepository.findAllBySubAccountId(id)
                .stream().map(pipelineMapper::toDto).toList();
    }

    @Transactional
    @Override
    @CacheEvict(value = "pipelines", allEntries = true)
    public void deleteBySA(String subAccountId) {
        pipelineRepository.deleteAllBySubAccountId(subAccountId);
    }
}
