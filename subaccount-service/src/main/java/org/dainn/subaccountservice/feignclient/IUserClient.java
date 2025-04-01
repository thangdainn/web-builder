package org.dainn.subaccountservice.feignclient;

import org.dainn.subaccountservice.dto.response.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/api/users", contextId = "userClient")
public interface IUserClient {
    @GetMapping("/{email}")
    ResponseEntity<UserDto> getByEmail(@PathVariable String email);
}
