package org.dainn.pipelineservice.feignclient;

import org.dainn.pipelineservice.dto.response.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/api/users")
public interface IUserClient {
    @GetMapping("/{id}")
    ResponseEntity<UserDto> getById(@PathVariable String id);
}
