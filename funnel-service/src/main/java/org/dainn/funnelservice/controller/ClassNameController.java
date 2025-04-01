package org.dainn.funnelservice.controller;


import lombok.RequiredArgsConstructor;
import org.dainn.funnelservice.config.endpoint.Endpoint;
import org.dainn.funnelservice.dto.ClassNameDto;
import org.dainn.funnelservice.service.IClassNameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.ClassName.BASE)
@RequiredArgsConstructor
public class ClassNameController {
    private final IClassNameService classNameService;

    @GetMapping(Endpoint.ClassName.ID)
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(classNameService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ClassNameDto dto) {
        return ResponseEntity.ok(classNameService.create(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        return ResponseEntity.ok(classNameService.delete(id));
    }
}
