package org.dainn.pipelineservice.controller;


import lombok.RequiredArgsConstructor;
import org.dainn.pipelineservice.config.endpoint.Endpoint;
import org.dainn.pipelineservice.dto.LaneDto;
import org.dainn.pipelineservice.service.ILaneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Lanes.BASE)
@RequiredArgsConstructor
public class LaneController {
    private final ILaneService laneService;

    @GetMapping(Endpoint.Lanes.ID)
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(laneService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LaneDto dto) {
        return ResponseEntity.ok(laneService.create(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        laneService.delete(id);
        return ResponseEntity.ok().build();
    }
}
