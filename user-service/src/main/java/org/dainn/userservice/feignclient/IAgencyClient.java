package org.dainn.userservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.dainn.userservice.dto.event.UserProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "agency-service", path = "/api/agencies")
public interface IAgencyClient {

    Logger log = LoggerFactory.getLogger(IAgencyClient.class);

//    @CircuitBreaker(name = "agencyService", fallbackMethod = "fallbackAgency")
//    @Bulkhead(name = "agencyService")
    @Retry(name = "agencyService", fallbackMethod = "fallbackAgency")
    @GetMapping("/{id}/detail")
    ResponseEntity<UserProducer.Agency> getDetailById(@PathVariable String id);

//    @CircuitBreaker(name = "agencyService", fallbackMethod = "fallbackAgency")
//    @Bulkhead(name = "agencyService")
    @Retry(name = "agencyService", fallbackMethod = "fallbackAgency")
    @GetMapping("/{id}")
    ResponseEntity<UserProducer.Agency> getById(@PathVariable String id);

    default ResponseEntity<UserProducer.Agency> fallbackAgency(String id, Throwable t) {
        log.warn("Fallback triggered for user service. Email: {}, Error: {}", id, t.getMessage());
        return ResponseEntity.ok(new UserProducer.Agency());
    }
}
