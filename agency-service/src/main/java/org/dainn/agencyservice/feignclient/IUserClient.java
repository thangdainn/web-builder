package org.dainn.agencyservice.feignclient;

import org.dainn.agencyservice.dto.response.UserOwnerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", path = "/api/users", contextId = "userClient")
public interface IUserClient {
    @PutMapping("/{email}/owner")
    ResponseEntity<Void> isOwner(@PathVariable String email, @RequestBody UserOwnerDto dto);
}
