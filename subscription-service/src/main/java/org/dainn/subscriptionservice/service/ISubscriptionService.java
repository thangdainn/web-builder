package org.dainn.subscriptionservice.service;


import org.dainn.subscriptionservice.dto.SubscriptionDto;
import reactor.core.publisher.Mono;

public interface ISubscriptionService {
    Mono<SubscriptionDto> create(SubscriptionDto dto);
    Mono<SubscriptionDto> update(SubscriptionDto dto);
    Mono<SubscriptionDto> findById(String id);
    Mono<SubscriptionDto> findByAgencyId(String id);
    Mono<SubscriptionDto> findByAgencyIdEvent(String id);
    Mono<Void> delete(String id);
}
