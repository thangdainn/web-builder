package org.dainn.funnelservice.service;

import org.dainn.funnelservice.dto.funnel.FunnelDetailDto;
import org.dainn.funnelservice.dto.funnel.FunnelDto;
import org.dainn.funnelservice.dto.funnel.FunnelReq;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

public interface IFunnelService {
    Mono<FunnelDto> create(FunnelDto dto);
    Mono<FunnelDto> update(FunnelDto dto);
    Mono<FunnelDto> updateLiveProducts(FunnelDto dto);
    Mono<FunnelDto> findById(String id);
    Mono<FunnelDetailDto> findDetailById(String id);
    Mono<FunnelDetailDto> findDetailByDomain(String domain);
    Mono<Void> delete(String id);
    Mono<Void> deleteBySA(String subAccountId);
    Mono<Page<FunnelDto>> findByFilters(String subAccountId, FunnelReq request);
}
