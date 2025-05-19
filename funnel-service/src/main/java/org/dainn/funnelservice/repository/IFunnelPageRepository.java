package org.dainn.funnelservice.repository;

import org.dainn.funnelservice.model.FunnelPage;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface IFunnelPageRepository extends R2dbcRepository<FunnelPage, String> {
    Flux<FunnelPage> findAllByFunnelIdOrderByOrderAsc(String funnelId);

    @Modifying
    @Query("""
                    UPDATE funnel_pages
                    SET name = :name
                    WHERE id = :id
            """)
    Mono<Integer> updateName(String id, String name);

    @Modifying
    @Query("""
                    UPDATE funnel_pages
                    SET content = :content
                    WHERE id = :id
            """)
    Mono<Integer> updateContent(String id, String content);
}
