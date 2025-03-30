package org.dainn.agencyservice.controller;


import lombok.RequiredArgsConstructor;
import org.dainn.agencyservice.config.endpoint.Endpoint;
import org.dainn.agencyservice.dto.AgencyDto;
import org.dainn.agencyservice.service.IAgencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Agency.BASE)
@RequiredArgsConstructor
public class AgencyController {
    private final IAgencyService agencyService;

    @GetMapping(Endpoint.Agency.ID)
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(agencyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AgencyDto dto) {
        return ResponseEntity.ok(agencyService.create(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        agencyService.delete(id);
        return ResponseEntity.ok().build();
    }
}
