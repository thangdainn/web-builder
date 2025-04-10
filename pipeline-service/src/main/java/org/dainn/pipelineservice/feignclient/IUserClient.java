package org.dainn.pipelineservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.dainn.pipelineservice.dto.response.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/api/users")
public interface IUserClient {
    @GetMapping("/{id}")
    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackUser")
    @Retry(name = "userService")
    @Bulkhead(name = "userService")
    ResponseEntity<UserDto> getById(@PathVariable String id);

    default ResponseEntity<UserDto> fallbackUser(String id, Throwable t) {
        System.out.println("Fallback for user service: " + t.getMessage());
        return ResponseEntity.ok(new UserDto("id", "unknown", "unknown"));
    }
}
