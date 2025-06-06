package org.dainn.agencyservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.dainn.agencyservice.dto.response.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", path = "/api/users")
public interface IUserClient {

    Logger log = LoggerFactory.getLogger(IUserClient.class);

    @Bulkhead(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackUser")
    @PutMapping("/{email}/owner")
    ResponseEntity<UserDto> setOwner(@PathVariable String email, @RequestBody UserDto dto);

    @Bulkhead(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackOwner")
    @PutMapping("/is-agency-owner")
    ResponseEntity<Boolean> isOwner(@RequestBody UserDto dto);


    default ResponseEntity<UserDto> fallbackUser(String email, UserDto dto, Throwable t) {
        log.warn("Fallback triggered for user service. Email: {}, Error: {}", email, t.getMessage());
        return ResponseEntity.ok(null);
    }

    default ResponseEntity<Boolean> fallbackOwner(UserDto dto, Throwable t) {
        log.warn("Fallback triggered for user service. Error: {}", t.getMessage());
        return ResponseEntity.ok(false);
    }
}
