package org.dainn.userservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.dainn.userservice.dto.event.UserProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "subaccount-service", path = "/api/sub-accounts")
public interface ISAClient {

    Logger log = LoggerFactory.getLogger(ISAClient.class);

//    @CircuitBreaker(name = "subaccountService", fallbackMethod = "fallbackSA")
//    @Bulkhead(name = "subaccountService")
    @Retry(name = "subaccountService", fallbackMethod = "fallbackSA")
    @GetMapping("/{id}")
    ResponseEntity<UserProducer.SubAccount> getById(@PathVariable String id);

//    @CircuitBreaker(name = "subaccountService", fallbackMethod = "fallbackSAs")
//    @Bulkhead(name = "subaccountService")
@   Retry(name = "subaccountService", fallbackMethod = "fallbackSAs")
    @GetMapping("/agency/{id}")
    ResponseEntity<List<UserProducer.SubAccount>> getAllByAgency(@PathVariable String id);

    default ResponseEntity<UserProducer.SubAccount> fallbackSA(String id, Throwable t) {
        log.warn("Fallback triggered for user service. Id: {}, Error: {}", id, t.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    default ResponseEntity<List<UserProducer.SubAccount>> fallbackSAs(String id, Throwable t) {
        log.warn("Fallback triggered for user service. Id: {}, Error: {}", id, t.getMessage());
        return ResponseEntity.ok(List.of(new UserProducer.SubAccount()));
    }
}
