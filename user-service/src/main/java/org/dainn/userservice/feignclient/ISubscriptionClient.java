package org.dainn.userservice.feignclient;

import io.github.resilience4j.retry.annotation.Retry;
import org.dainn.userservice.dto.response.SubscriptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "subscription-service", path = "/api/subscriptions")
public interface ISubscriptionClient {

    Logger log = LoggerFactory.getLogger(ISubscriptionClient.class);

    @Retry(name = "subscriptionService", fallbackMethod = "fallbackSubscription")
//    @CircuitBreaker(name = "subscriptionService", fallbackMethod = "fallbackSubscription")
//    @Bulkhead(name = "subscriptionService")
    @GetMapping("/agency/{id}")
    ResponseEntity<SubscriptionDto> getByAgencyId(@PathVariable String id);

    default ResponseEntity<SubscriptionDto> fallbackSubscription(String id, Throwable t) {
        log.warn("Fallback triggered for get subs by agency. Id: {}, Error: {}", id, t.getMessage());
        return ResponseEntity.ok(null);
    }
}
