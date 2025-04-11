package org.dainn.subaccountservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.dainn.subaccountservice.dto.response.TagDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "pipeline-service", path = "/api/tags", contextId = "tagClient")
public interface ITagClient {

    Logger log = LoggerFactory.getLogger(ITagClient.class);

    @CircuitBreaker(name = "pipeService", fallbackMethod = "fallbackTag")
    @Retry(name = "pipeService")
    @Bulkhead(name = "pipeService")
    @GetMapping("/sub-accounts/{id}")
    ResponseEntity<List<TagDto>> getBySubAccount(@PathVariable String id);

    default ResponseEntity<List<TagDto>> fallbackTag(String id, Throwable t) {
        log.warn("Fallback triggered for tag service. ID: {}, Error: {}", id, t.getMessage());
        return ResponseEntity.ok(List.of(new TagDto("unknown", "unknown", "unknown")));
    }
}
