package org.dainn.subaccountservice.feignclient;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.dainn.subaccountservice.dto.response.PipelineDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "pipeline-service", path = "/api/pipelines", contextId = "pipelineClient")
public interface IPipelineClient {
    @PostMapping
    ResponseEntity<PipelineDto> create(@RequestBody PipelineDto dto);
}
