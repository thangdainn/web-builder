package org.dainn.funnelservice.service;

import org.dainn.funnelservice.dto.ClassNameDto;
import reactor.core.publisher.Mono;

public interface IClassNameService {
    Mono<ClassNameDto> create(ClassNameDto dto);
    Mono<ClassNameDto> update(ClassNameDto dto);
    Mono<ClassNameDto> findById(String id);
    Mono<Void> delete(String id);
}
