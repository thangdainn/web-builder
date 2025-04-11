package org.dainn.agencyservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.dainn.agencyservice.dto.SubscriptionResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@FeignClient(name = "subscription-service", path = "/api/subscriptions")
public interface ISubscriptionClient {
    Logger log = LoggerFactory.getLogger(ISubscriptionClient.class);

    @CircuitBreaker(name = "subscriptionService", fallbackMethod = "fallbackSubscription")
    @Retry(name = "subscriptionService")
    @Bulkhead(name = "subscriptionService")
    @GetMapping("/agency/{id}")
    List<SubscriptionResp> getByAgencyId(@PathVariable String id);

    default List<SubscriptionResp> fallbackSubscription(String id, Throwable t) {
        log.warn("Fallback triggered for subscription service. ID: {}, Error: {}", id, t.getMessage(), t);
        return List.of(new SubscriptionResp("unknown", BigDecimal.ZERO, LocalDateTime.now(), false, "unknown", "unknown"));
    }
}
