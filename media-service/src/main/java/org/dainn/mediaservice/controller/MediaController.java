package org.dainn.mediaservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.mediaservice.config.endpoint.Endpoint;
import org.dainn.mediaservice.dto.MediaDto;
import org.dainn.mediaservice.dto.MediaReq;
import org.dainn.mediaservice.service.IMediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(Endpoint.Media.BASE)
@RequiredArgsConstructor
public class MediaController {
    private final IMediaService mediaService;

    @GetMapping(Endpoint.Media.ID)
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(mediaService.findById(id));
    }

    @GetMapping(Endpoint.Media.SUB_ACCOUNT)
    public ResponseEntity<?> getByFilters(@PathVariable String id, @ModelAttribute MediaReq request) {
        request.setSubAccountId(id);
        return ResponseEntity.ok(mediaService.findByFilters(request));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody MediaDto dto) {
        return ResponseEntity.ok(mediaService.create(dto));
    }

    @PostMapping(Endpoint.Media.UPLOAD)
    public ResponseEntity<?> upload(@Valid @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(mediaService.upload(file));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        mediaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
