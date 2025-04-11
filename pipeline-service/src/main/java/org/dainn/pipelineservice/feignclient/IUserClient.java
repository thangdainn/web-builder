package org.dainn.pipelineservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.dainn.pipelineservice.dto.response.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/api/users")
public interface IUserClient {

    Logger log = LoggerFactory.getLogger(IUserClient.class);

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackUser")
    @Retry(name = "userService")
    @Bulkhead(name = "userService")
    @GetMapping("/{id}")
    ResponseEntity<UserDto> getById(@PathVariable String id);

    default ResponseEntity<UserDto> fallbackUser(String id, Throwable t) {
        log.warn("Fallback triggered for user service. ID: {}, Error: {}", id, t.getMessage(), t);
        return ResponseEntity.ok(new UserDto("id", "unknown", "unknown"));
    }
}
