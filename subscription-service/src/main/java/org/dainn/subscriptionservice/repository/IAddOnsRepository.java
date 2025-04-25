package org.dainn.subscriptionservice.repository;

import org.dainn.subscriptionservice.model.AddOns;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IAddOnsRepository extends R2dbcRepository<AddOns, String> {
    @Modifying
    Mono<Void> deleteByAgencyId(String agencyId);
}
