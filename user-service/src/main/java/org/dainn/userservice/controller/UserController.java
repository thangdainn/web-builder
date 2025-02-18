package org.dainn.userservice.controller;


import lombok.RequiredArgsConstructor;
import org.dainn.userservice.dto.UserDto;
import org.dainn.userservice.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
