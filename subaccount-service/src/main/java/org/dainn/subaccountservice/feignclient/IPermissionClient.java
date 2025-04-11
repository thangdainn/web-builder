package org.dainn.subaccountservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.dainn.subaccountservice.dto.response.PermissionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", path = "/api/permissions", contextId = "permissionClient")
public interface IPermissionClient {

    Logger log = LoggerFactory.getLogger(IPermissionClient.class);

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackPermission")
    @Retry(name = "userService")
    @Bulkhead(name = "userService")
    @PostMapping
    ResponseEntity<PermissionDto> create(@RequestBody PermissionDto dto);

    default ResponseEntity<PermissionDto> fallbackPermission(PermissionDto dto, Throwable t) {
        log.warn("Fallback triggered for permission service. PermissionDto: {}, Error: {}", dto, t.getMessage(), t);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new PermissionDto("unknown", "unknown", "unknown", "unknown", false));
    }
}
