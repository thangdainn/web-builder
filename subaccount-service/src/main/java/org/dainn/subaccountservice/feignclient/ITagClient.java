package org.dainn.subaccountservice.feignclient;

import org.dainn.subaccountservice.dto.response.TagDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "pipeline-service", path = "/api/tags", contextId = "tagClient")
public interface ITagClient {
    @GetMapping("/sub-accounts/{id}")
    ResponseEntity<List<TagDto>> getBySubAccount(@PathVariable String id);
}
