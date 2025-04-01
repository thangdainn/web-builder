package org.dainn.subscriptionservice.repository;

import org.dainn.subscriptionservice.model.Subscription;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ISubscriptionRepository extends R2dbcRepository<Subscription, String> {
    Flux<Subscription> findAllByAgencyId(String agencyId);
}
