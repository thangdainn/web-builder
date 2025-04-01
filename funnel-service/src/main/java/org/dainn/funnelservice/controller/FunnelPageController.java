package org.dainn.funnelservice.controller;


import lombok.RequiredArgsConstructor;
import org.dainn.funnelservice.config.endpoint.Endpoint;
import org.dainn.funnelservice.dto.FunnelPageDto;
import org.dainn.funnelservice.service.IFunnelPageService;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(funnelPageService.create(dto));
    }

    @PutMapping(Endpoint.FunnelPage.ID)
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody FunnelPageDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(funnelPageService.update(dto));
    }

    @PutMapping(Endpoint.FunnelPage.NAME)
    public ResponseEntity<?> updateName(@PathVariable String id, @RequestBody FunnelPageDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(funnelPageService.updateName(dto));
    }

    @PutMapping(Endpoint.FunnelPage.CONTENT)
    public ResponseEntity<?> updateContent(@PathVariable String id, @RequestBody FunnelPageDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(funnelPageService.updateContent(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        return ResponseEntity.ok(funnelPageService.delete(id));
    }
}
