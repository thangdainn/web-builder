package org.dainn.pipelineservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.dainn.pipelineservice.dto.response.ContactDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "subaccount-service", path = "/api/contacts")
public interface IContactClient {
    @GetMapping("/{id}")
    @CircuitBreaker(name = "contactService", fallbackMethod = "fallbackContact")
    @Retry(name = "contactService")
    @Bulkhead(name = "contactService")
    ResponseEntity<ContactDto> getById(@PathVariable String id);

    default ResponseEntity<ContactDto> fallbackContact(String id, Throwable t) {
        System.out.println("Fallback for subaccount service: " + t.getMessage());

        return ResponseEntity.ok(new ContactDto("unknown", "unknown"));
    }
}
