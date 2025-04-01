package org.dainn.pipelineservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.pipelineservice.dto.tag.TagDto;
import org.dainn.pipelineservice.exception.AppException;
import org.dainn.pipelineservice.exception.ErrorCode;
import org.dainn.pipelineservice.mapper.ITagMapper;
import org.dainn.pipelineservice.model.Tag;
import org.dainn.pipelineservice.repository.ITagRepository;
import org.dainn.pipelineservice.service.ITagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService implements ITagService {
    private final ITagRepository tagRepository;
    private final ITagMapper tagMapper;

    @Transactional
    @Override
    public TagDto create(TagDto dto) {
        Tag tag = tagMapper.toEntity(dto);
        return tagMapper.toDto(tagRepository.save(tag));
    }

    @Override
    public TagDto findById(String id) {
        return tagMapper.toDto(tagRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_EXISTED)));
    }

    @Transactional
    @Override
    public TagDto update(TagDto dto) {
        Tag tag = tagRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_EXISTED));
        tag = tagMapper.toUpdate(tag, dto);
        return tagMapper.toDto(tagRepository.save(tag));
    }

    @Transactional
    @Override
    public void delete(String id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<TagDto> findBySubAccount(String subAccountId) {
        return tagRepository.findAllBySubAccountId(subAccountId)
                .stream().map(tagMapper::toDto).toList();
    }
}
