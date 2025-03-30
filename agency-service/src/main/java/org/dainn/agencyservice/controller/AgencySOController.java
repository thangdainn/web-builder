package org.dainn.agencyservice.controller;


import lombok.RequiredArgsConstructor;
import org.dainn.agencyservice.config.endpoint.Endpoint;
import org.dainn.agencyservice.dto.AgencySODto;
import org.dainn.agencyservice.service.IAgencySOService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.AgencySO.BASE)
@RequiredArgsConstructor
public class AgencySOController {
    private final IAgencySOService agencySOService;

    @GetMapping(Endpoint.AgencySO.ID)
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(agencySOService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AgencySODto dto) {
        return ResponseEntity.ok(agencySOService.create(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        agencySOService.delete(id);
        return ResponseEntity.ok().build();
    }
}
