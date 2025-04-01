package org.dainn.funnelservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.funnelservice.config.endpoint.Endpoint;
import org.dainn.funnelservice.dto.funnel.FunnelDto;
import org.dainn.funnelservice.dto.funnel.FunnelReq;
import org.dainn.funnelservice.service.IFunnelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Funnel.BASE)
@RequiredArgsConstructor
public class FunnelController {
    private final IFunnelService funnelService;

    @GetMapping
    public ResponseEntity<?> findAll(@ModelAttribute FunnelReq request) {
        return ResponseEntity.ok(funnelService.findByFilters(request));
    }

    @GetMapping(Endpoint.Funnel.ID)
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.ok(funnelService.findById(id));
    }

    @GetMapping(Endpoint.Funnel.DETAIL)
    public ResponseEntity<?> getDetailById(@PathVariable String id) {
        return ResponseEntity.ok(funnelService.findDetailById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody FunnelDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(funnelService.create(dto));
    }

    @PutMapping(Endpoint.Funnel.ID)
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody FunnelDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(funnelService.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        return ResponseEntity.ok(funnelService.delete(id));
    }
}
