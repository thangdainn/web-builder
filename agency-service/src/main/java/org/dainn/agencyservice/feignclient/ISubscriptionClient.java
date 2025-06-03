package org.dainn.agencyservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.dainn.agencyservice.dto.SubscriptionResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "subscription-service", path = "/api/subscriptions")
public interface ISubscriptionClient {
    Logger log = LoggerFactory.getLogger(ISubscriptionClient.class);

    @Bulkhead(name = "subscriptionService")
    @CircuitBreaker(name = "subscriptionService", fallbackMethod = "fallbackSubscription")
    @GetMapping("/agency/{id}")
    ResponseEntity<SubscriptionResp> getByAgencyId(@PathVariable String id);

    default SubscriptionResp fallbackSubscription(String id, Throwable t) {
        log.warn("Fallback triggered for subscription service. Agency ID: {}, Error: {}", id, t.getMessage());
        return new SubscriptionResp();
    }
}
