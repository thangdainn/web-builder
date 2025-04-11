package org.dainn.agencyservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.dainn.agencyservice.dto.response.UserOwnerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", path = "/api/users", contextId = "userClient")
public interface IUserClient {

    Logger log = LoggerFactory.getLogger(IUserClient.class);

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackUser")
    @Retry(name = "userService")
    @Bulkhead(name = "userService")
    @PutMapping("/{email}/owner")
    ResponseEntity<Void> isOwner(@PathVariable String email, @RequestBody UserOwnerDto dto);

    default ResponseEntity<Void> fallbackUser(String email, UserOwnerDto dto, Throwable t) {
        log.warn("Fallback triggered for user service. Email: {}, Error: {}", email, t.getMessage(), t);
        return ResponseEntity.ok().build();
    }
}
