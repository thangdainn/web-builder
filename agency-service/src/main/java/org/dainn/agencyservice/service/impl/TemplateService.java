package org.dainn.agencyservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.agencyservice.dto.TemplateDto;
import org.dainn.agencyservice.mapper.ITemplateMapper;
import org.dainn.agencyservice.model.Template;
import org.dainn.agencyservice.repository.ITemplateRepository;
import org.dainn.agencyservice.service.ITemplateService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateService implements ITemplateService {
    private final ITemplateRepository templateRepository;
    private final ITemplateMapper templateMapper;

    @Transactional
    @Override
    @CacheEvict(value = "templates", allEntries = true)
    public TemplateDto create(TemplateDto dto) {
        Template template = templateMapper.toEntity(dto);
        return templateMapper.toDto(templateRepository.save(template));
    }

    @Override
    @Cacheable(value = "templates", key = "'all'")
    public List<TemplateDto> findAll() {
        return templateRepository.findAll()
                .stream()
                .map(templateMapper::toDto)
                .toList();
    }
}
