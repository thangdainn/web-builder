package org.dainn.funnelservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.funnelservice.dto.ClassNameDto;
import org.dainn.funnelservice.mapper.IClassNameMapper;
import org.dainn.funnelservice.repository.IClassNameRepository;
import org.dainn.funnelservice.service.IClassNameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClassNameService implements IClassNameService {
    private final IClassNameRepository classNameRepository;
    private final IClassNameMapper classNameMapper;

    @Transactional
    @Override
    public Mono<ClassNameDto> create(ClassNameDto dto) {
        return null;
    }

    @Transactional
    @Override
    public Mono<ClassNameDto> update(ClassNameDto dto) {
        return null;
    }

    @Override
    public Mono<ClassNameDto> findById(String id) {
        return null;
    }

    @Transactional
    @Override
    public Mono<Void> delete(String id) {
        return null;
    }
}
