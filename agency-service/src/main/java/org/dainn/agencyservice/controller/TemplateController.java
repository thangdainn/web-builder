package org.dainn.agencyservice.controller;


import lombok.RequiredArgsConstructor;
import org.dainn.agencyservice.config.endpoint.Endpoint;
import org.dainn.agencyservice.dto.TemplateDto;
import org.dainn.agencyservice.service.ITemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Template.BASE)
@RequiredArgsConstructor
public class TemplateController {
    private final ITemplateService templateService;

    @GetMapping()
    public ResponseEntity<?> gets() {
        return ResponseEntity.ok(templateService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TemplateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(templateService.create(dto));
    }
}
