package org.dainn.userservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.userservice.config.endpoint.Endpoint;
import org.dainn.userservice.dto.permission.PermissionDto;
import org.dainn.userservice.event.EventProducer;
import org.dainn.userservice.service.IPermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Permission.BASE)
@RequiredArgsConstructor
public class PermissionController {
    private final IPermissionService permissionService;
    private final EventProducer eventProducer;

    @GetMapping(Endpoint.Permission.USER)
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.ok(permissionService.findAllByUser(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PermissionDto dto) {
        dto = permissionService.create(dto);
        eventProducer.changePerEvent(dto.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping(Endpoint.Permission.ACCESS)
    public ResponseEntity<?> updateAccess(@PathVariable String id, @RequestBody PermissionDto dto) {
        permissionService.updateAccess(id, dto.getAccess());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(String id) {
        permissionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
