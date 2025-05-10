package org.dainn.subscriptionservice.repository;

import org.dainn.subscriptionservice.model.Subscription;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ISubscriptionRepository extends R2dbcRepository<Subscription, String> {
    Mono<Subscription> findByAgencyId(String agencyId);
    Mono<Subscription> findByCustomerId(String customerId);


    @Modifying
    Mono<Void> deleteByAgencyId(String agencyId);
}
