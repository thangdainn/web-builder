package org.dainn.mediaservice.repository;

import org.dainn.mediaservice.model.Media;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface IMediaRepository extends R2dbcRepository<Media, String> {
    Flux<Media> findBySubAccountId(String subAccountId, Pageable pageable);
    Flux<Media> findByNameContainingIgnoreCaseAndSubAccountId(String name, String subAccountId, Pageable pageable);
}
