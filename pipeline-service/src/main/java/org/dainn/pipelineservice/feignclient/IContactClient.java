package org.dainn.pipelineservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.dainn.pipelineservice.dto.response.ContactDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/api/contacts", contextId = "contactClient")
public interface IContactClient {

    Logger log = LoggerFactory.getLogger(IContactClient.class);

    @CircuitBreaker(name = "contactService", fallbackMethod = "fallbackContact")
    @Bulkhead(name = "contactService")
    @GetMapping("/{id}")
    ResponseEntity<ContactDto> getById(@PathVariable String id);

    default ResponseEntity<ContactDto> fallbackContact(String id, Throwable t) {
        log.warn("Fallback triggered for contact service. ID: {}, Error: {}", id, t.getMessage());
        return ResponseEntity.ok(new ContactDto("unknown", "unknown"));
    }
}
