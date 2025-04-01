package org.dainn.pipelineservice.service;

import org.dainn.pipelineservice.dto.tag.TagDto;

import java.util.List;

public interface ITagService {
    TagDto create(TagDto dto);
    TagDto findById(String id);
    TagDto update(TagDto dto);
    void delete(String id);
    List<TagDto> findBySubAccount(String subAccountId);
}
