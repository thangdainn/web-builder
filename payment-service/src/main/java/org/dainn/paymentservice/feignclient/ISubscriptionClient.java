package org.dainn.paymentservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.dainn.paymentservice.dto.response.SubscriptionResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "subscription-service", path = "/api/subscriptions")
public interface ISubscriptionClient {
    Logger log = LoggerFactory.getLogger(ISubscriptionClient.class);

    @Bulkhead(name = "subscriptionService")
    @CircuitBreaker(name = "subscriptionService", fallbackMethod = "fallbackSubscription")
//    @TimeLimiter(name = "subscriptionService")
    @GetMapping("/agency/{id}")
    List<SubscriptionResp> getByAgencyId(@PathVariable String id);

    default List<SubscriptionResp> fallbackSubscription(String id, Throwable t) {
        log.warn("Fallback triggered for subscription service. Agency ID: {}, Error: {}", id, t.getMessage());
        return List.of(new SubscriptionResp());
    }
}
