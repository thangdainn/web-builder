package org.dainn.funnelservice.repository;

import org.dainn.funnelservice.model.Funnel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface IFunnelRepository extends R2dbcRepository<Funnel, String> {
    Flux<Funnel> findAllBySubAccountIdAndNameContainingIgnoreCase(String subAccountId, String name, Pageable pageable);

    Flux<Funnel> findAllBySubAccountId(String subAccountId, Pageable pageable);
    Flux<Funnel> findAllBySubAccountId(String subAccountId);

    Mono<Funnel> findBySubDomainName(String subDomainName);

    @Modifying
    Mono<Void> deleteAllBySubAccountId(String subAccountId);

    @Modifying
    @Query("""
            UPDATE funnels
            SET live_products = :liveProducts
            WHERE id = :id
            """)
    Mono<Integer> updateLiveProducts(String id, String liveProducts);

    Mono<Boolean> existsBySubDomainName(String subDomainName);
}
