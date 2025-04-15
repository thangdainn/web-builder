package org.dainn.paymentservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.dainn.paymentservice.dto.response.AgencyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "agency-service", path = "/api/agencies")
public interface IAgencyClient {

    Logger log = LoggerFactory.getLogger(IAgencyClient.class);

    @Bulkhead(name = "agnecyService")
    @CircuitBreaker(name = "agnecyService", fallbackMethod = "fallbackAgency")
    @GetMapping("/customer/{id}")
    ResponseEntity<AgencyDto> getByCustomerId(@PathVariable String id);

    default ResponseEntity<AgencyDto> fallbackAgency(String id, Throwable t) {
        log.warn("Fallback triggered for user service. id: {}, Error: {}", id, t.getMessage());
        return ResponseEntity.ok(new AgencyDto());
    }
}
