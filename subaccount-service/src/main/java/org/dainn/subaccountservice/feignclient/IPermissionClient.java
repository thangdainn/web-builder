package org.dainn.subaccountservice.feignclient;

import org.dainn.subaccountservice.dto.PermissionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", path = "/api/permissions", contextId = "permissionClient")
public interface IPermissionClient {
    @PostMapping
    ResponseEntity<PermissionDto> create(@RequestBody PermissionDto dto);
}
