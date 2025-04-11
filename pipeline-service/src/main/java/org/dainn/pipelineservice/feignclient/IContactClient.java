package org.dainn.pipelineservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.dainn.pipelineservice.dto.response.ContactDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "subaccount-service", path = "/api/contacts")
public interface IContactClient {

    Logger log = LoggerFactory.getLogger(IContactClient.class);

    @CircuitBreaker(name = "contactService", fallbackMethod = "fallbackContact")
    @Retry(name = "contactService")
    @Bulkhead(name = "contactService")
    @GetMapping("/{id}")
    ResponseEntity<ContactDto> getById(@PathVariable String id);

    default ResponseEntity<ContactDto> fallbackContact(String id, Throwable t) {
        log.warn("Fallback triggered for contact service. ID: {}, Error: {}", id, t.getMessage(), t);
        return ResponseEntity.ok(new ContactDto("unknown", "unknown"));
    }
}
