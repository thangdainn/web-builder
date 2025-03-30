package org.dainn.funnelservice.controller;


import lombok.RequiredArgsConstructor;
import org.dainn.funnelservice.config.endpoint.Endpoint;
import org.dainn.funnelservice.dto.FunnelDto;
import org.dainn.funnelservice.service.IFunnelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Funnel.BASE)
@RequiredArgsConstructor
public class FunnelController {
    private final IFunnelService funnelService;

    @GetMapping(Endpoint.Funnel.ID)
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(funnelService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody FunnelDto dto) {
        return ResponseEntity.ok(funnelService.create(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        funnelService.delete(id);
        return ResponseEntity.ok().build();
    }
}
