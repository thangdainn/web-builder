package org.dainn.userservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.userservice.config.endpoint.Endpoint;
import org.dainn.userservice.dto.user.UserDetailDto;
import org.dainn.userservice.dto.user.UserDto;
import org.dainn.userservice.dto.user.UserReq;
import org.dainn.userservice.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
//    @PreAuthorize("hasRole('MEMBER')")
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

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(String id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
