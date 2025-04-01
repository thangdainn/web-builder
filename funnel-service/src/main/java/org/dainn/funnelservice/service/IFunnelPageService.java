package org.dainn.funnelservice.service;

import org.dainn.funnelservice.dto.FunnelPageDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFunnelPageService {
    Mono<FunnelPageDto> create(FunnelPageDto dto);
    Mono<FunnelPageDto> update(FunnelPageDto dto);
    Mono<FunnelPageDto> updateName(FunnelPageDto dto);
    Mono<FunnelPageDto> updateContent(FunnelPageDto dto);
    Mono<FunnelPageDto> findById(String id);
    Mono<Void> delete(String id);
    Flux<FunnelPageDto> findByFunnel(String funnelId);
}
