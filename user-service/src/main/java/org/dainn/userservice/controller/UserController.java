package org.dainn.userservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.userservice.config.endpoint.Endpoint;
import org.dainn.userservice.dto.user.UserDetailDto;
import org.dainn.userservice.dto.user.UserDto;
import org.dainn.userservice.dto.user.UserReq;
import org.dainn.userservice.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(Endpoint.User.BASE)
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping
    public ResponseEntity<?> getAll(UserReq userReq) {
        return ResponseEntity.ok(userService.findAll(userReq));
    }

    @GetMapping(Endpoint.User.DETAIL)
    public ResponseEntity<UserDetailDto> getDetailById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findDetailById(id));
    }

    @GetMapping(Endpoint.User.ID)
    public ResponseEntity<UserDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping(Endpoint.User.EMAIL)
    public ResponseEntity<UserDto> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping(Endpoint.User.IS_OWNER)
    public ResponseEntity<Boolean> isOwner(@PathVariable String id) {
        return ResponseEntity.ok(userService.isOwner(id));
    }

    @PostMapping(Endpoint.User.IS_OWNER_AGENCY)
    public ResponseEntity<Boolean> isOwnerAgency(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.isOwnerAgency(dto));
    }

    @PostMapping(Endpoint.User.SYNC)
    public ResponseEntity<Void> syncPermission() {
        userService.syncUser();
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(dto));
    }

    @PutMapping(Endpoint.User.SET_OWNER)
    public ResponseEntity<Void> setOwner(@PathVariable String email, @RequestBody UserDto dto) {
        dto.setEmail(email);
        userService.setOwner(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(Endpoint.User.EMAIL)
    public ResponseEntity<UserDto> update(@PathVariable String email, @RequestBody UserDto dto) {
        dto.setEmail(email);
        return ResponseEntity.ok(userService.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(String id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
