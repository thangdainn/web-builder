package org.dainn.subaccountservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.dainn.subaccountservice.dto.response.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/api/users", contextId = "userClient")
public interface IUserClient {

    Logger log = LoggerFactory.getLogger(IUserClient.class);

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackUser")
    @Retry(name = "userService")
    @Bulkhead(name = "userService")
    @GetMapping("/email/{email}")
    ResponseEntity<UserDto> getByEmail(@PathVariable String email);

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackUserIsOwner")
    @Retry(name = "userService")
    @Bulkhead(name = "userService")
    @GetMapping("/{id}/is-owner")
    ResponseEntity<Boolean> isOwner(@PathVariable String id);

    default ResponseEntity<UserDto> fallbackUser(String email, Throwable t) {
        log.warn("Fallback triggered for user service. Email: {}, Error: {}", email, t.getMessage(), t);
        return ResponseEntity.ok(new UserDto("unknown", "unknown", "unknown"));
    }

    default ResponseEntity<Boolean> fallbackUserIsOwner(String id, Throwable t) {
        log.warn("Fallback triggered for user service. ID: {}, Error: {}", id, t.getMessage(), t);
        return ResponseEntity.ok(false);
    }
}
