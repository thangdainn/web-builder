package org.dainn.funnelservice.controller;


import lombok.RequiredArgsConstructor;
import org.dainn.funnelservice.config.endpoint.Endpoint;
import org.dainn.funnelservice.dto.FunnelPageDto;
import org.dainn.funnelservice.service.IFunnelPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.FunnelPage.BASE)
@RequiredArgsConstructor
public class FunnelPageController {
    private final IFunnelPageService funnelPageService;

    @GetMapping(Endpoint.FunnelPage.ID)
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(funnelPageService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody FunnelPageDto dto) {
        return ResponseEntity.ok(funnelPageService.create(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        funnelPageService.delete(id);
        return ResponseEntity.ok().build();
    }
}
