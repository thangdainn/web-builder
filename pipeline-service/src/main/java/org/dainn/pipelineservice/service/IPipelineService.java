package org.dainn.pipelineservice.service;

import org.dainn.pipelineservice.dto.pipeline.PipelineDto;
import org.dainn.pipelineservice.dto.pipeline.UpdatePipelineDto;

import java.util.List;

public interface IPipelineService {
    PipelineDto create(PipelineDto dto);
    PipelineDto findById(String id);
    PipelineDto update(PipelineDto dto);
    void updateName(String id, UpdatePipelineDto dto);
    void delete(String id);
    List<PipelineDto> findBySA(String id);

    void deleteBySA(String subAccountId);
}
