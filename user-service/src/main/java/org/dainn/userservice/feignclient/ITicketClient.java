package org.dainn.userservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.dainn.userservice.dto.response.PipelineDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "pipeline-service", path = "/api/tickets", contextId = "ticketClient")
public interface ITicketClient {

    Logger log = LoggerFactory.getLogger(ITicketClient.class);

    @CircuitBreaker(name = "pipeService", fallbackMethod = "fallbackTicket")
    @Bulkhead(name = "pipeService")
    @PostMapping("/contacts/{id}/isAssigned")
    ResponseEntity<Boolean> isAssigned(@PathVariable String id);

    default ResponseEntity<Boolean> fallbackTicket(String id, Throwable t) {
        log.warn("Fallback triggered for ticket. Id: {}, Error: {}", id, t.getMessage());
        return ResponseEntity.ok(false);
    }
}
