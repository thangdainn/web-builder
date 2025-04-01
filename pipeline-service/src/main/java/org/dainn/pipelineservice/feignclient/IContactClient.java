package org.dainn.pipelineservice.feignclient;

import org.dainn.pipelineservice.dto.response.ContactDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "subaccount-service", path = "/api/contacts")
public interface IContactClient {
    @GetMapping("/{id}")
    ResponseEntity<ContactDto> getById(@PathVariable String id);
}
