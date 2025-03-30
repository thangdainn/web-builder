package org.dainn.pipelineservice.controller;


import lombok.RequiredArgsConstructor;
import org.dainn.pipelineservice.config.endpoint.Endpoint;
import org.dainn.pipelineservice.dto.PipelineDto;
import org.dainn.pipelineservice.service.IPipelineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Pipeline.BASE)
@RequiredArgsConstructor
public class PipelineController {
    private final IPipelineService pipelineService;

    @GetMapping(Endpoint.Pipeline.ID)
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(pipelineService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PipelineDto dto) {
        return ResponseEntity.ok(pipelineService.create(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        pipelineService.delete(id);
        return ResponseEntity.ok().build();
    }
}
