package org.dainn.funnelservice.service;

import org.dainn.funnelservice.dto.FPOrderDto;
import org.dainn.funnelservice.dto.FunnelPageDto;
import org.dainn.funnelservice.dto.UpdateFPDto;
import org.dainn.funnelservice.dto.event.FPOrderEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IFunnelPageService {
    Mono<FunnelPageDto> create(FunnelPageDto dto);
    Mono<FunnelPageDto> update(UpdateFPDto dto);
    Mono<FunnelPageDto> updateName(FunnelPageDto dto);
    Mono<FunnelPageDto> updateContent(FunnelPageDto dto);
    Mono<FunnelPageDto> updateVisits(FunnelPageDto dto);
    Mono<Void> changeOrderWithKafka(String funnelId, List<FPOrderDto> list);
    Mono<Void> changeOrder(FPOrderEvent data);
    Mono<FunnelPageDto> findById(String id);
    Mono<Void> delete(String id);
    Flux<FunnelPageDto> findByFunnel(String funnelId);
}
