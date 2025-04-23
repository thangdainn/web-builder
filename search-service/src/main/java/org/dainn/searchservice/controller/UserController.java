package org.dainn.searchservice.controller;


import lombok.RequiredArgsConstructor;
import org.dainn.searchservice.config.endpoint.Endpoint;
import org.dainn.searchservice.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Endpoint.User.BASE)
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping(Endpoint.User.SUB_TEAM_MEMBER)
    public ResponseEntity<?> getSubAccountTeamMembers(@PathVariable String subAccountId) {
        return ResponseEntity.ok(userService.getSubAccountTeamMembers(subAccountId));
    }

    @GetMapping(Endpoint.User.TEAM_MEMBER)
    public ResponseEntity<?> getTeamMembers(@PathVariable String agencyId) {
        return ResponseEntity.ok(userService.getTeamMembers(agencyId));
    }

    @GetMapping(Endpoint.User.ID)
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping(Endpoint.User.EMAIL)
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }
}
