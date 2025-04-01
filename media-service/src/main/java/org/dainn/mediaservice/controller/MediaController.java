package org.dainn.mediaservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.mediaservice.config.endpoint.Endpoint;
import org.dainn.mediaservice.dto.MediaDto;
import org.dainn.mediaservice.dto.MediaReq;
import org.dainn.mediaservice.service.IMediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Media.BASE)
@RequiredArgsConstructor
public class MediaController {
    private final IMediaService mediaService;

    @GetMapping(Endpoint.Media.ID)
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(mediaService.findById(id));
    }

    @GetMapping(Endpoint.Media.SUBACCOUNT)
    public ResponseEntity<?> getByFilters(@PathVariable String id, @ModelAttribute MediaReq request) {
        request.setSubAccountId(id);
        return ResponseEntity.ok(mediaService.findByFilters(request));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody MediaDto dto) {
        return ResponseEntity.ok(mediaService.create(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        return ResponseEntity.ok(mediaService.delete(id));
    }
}
