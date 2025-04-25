package org.dainn.funnelservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.funnelservice.dto.ClassNameDto;
import org.dainn.funnelservice.exception.AppException;
import org.dainn.funnelservice.exception.ErrorCode;
import org.dainn.funnelservice.mapper.IClassNameMapper;
import org.dainn.funnelservice.model.ClassName;
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
        ClassName className = classNameMapper.toEntity(dto);
        className.markNew();
        return classNameRepository.save(className)
                .map(classNameMapper::toDto);
    }

    @Transactional
    @Override
    public Mono<ClassNameDto> update(ClassNameDto dto) {
        return classNameRepository.findById(dto.getId())
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.CLASS_NAME_NOT_EXISTED)))
                .flatMap(existing -> {
                    existing = classNameMapper.toUpdate(existing, dto);
                    existing.markExisting();
                    return classNameRepository.save(existing);
                })
                .map(classNameMapper::toDto);
    }

    @Override
    public Mono<ClassNameDto> findById(String id) {
        return classNameRepository.findById(id)
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.CLASS_NAME_NOT_EXISTED)))
                .map(classNameMapper::toDto);
    }

    @Transactional
    @Override
    public Mono<Void> delete(String id) {
        return classNameRepository.deleteById(id);
    }
}
