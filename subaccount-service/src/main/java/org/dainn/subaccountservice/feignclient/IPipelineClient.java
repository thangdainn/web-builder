package org.dainn.subaccountservice.feignclient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.dainn.subaccountservice.dto.response.PipelineDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "pipeline-service", path = "/api/pipelines", contextId = "pipelineClient")
public interface IPipelineClient {

    Logger log = LoggerFactory.getLogger(IPipelineClient.class);

    @CircuitBreaker(name = "pipeService", fallbackMethod = "fallbackPipeline")
    @Retry(name = "pipeService")
    @Bulkhead(name = "pipeService")
    @PostMapping
    ResponseEntity<PipelineDto> create(@RequestBody PipelineDto dto);

    default ResponseEntity<PipelineDto> fallbackPipeline(PipelineDto dto, Throwable t) {
        log.warn("Fallback triggered for pipeline service. PipelineDto: {}, Error: {}", dto, t.getMessage(), t);
        return ResponseEntity.ok(new PipelineDto("unknown", "unknown"));
    }
}
