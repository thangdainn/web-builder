package org.dainn.pipelineservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.pipelineservice.config.endpoint.Endpoint;
import org.dainn.pipelineservice.dto.lane.LaneDto;
import org.dainn.pipelineservice.service.ILaneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Lane.BASE)
@RequiredArgsConstructor
public class LaneController {
    private final ILaneService laneService;

    @GetMapping(Endpoint.Lane.ID)
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(laneService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody LaneDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(laneService.create(dto));
    }

    @PutMapping(Endpoint.Lane.ID)
    public ResponseEntity<?> updateOrder(@PathVariable String id, @Valid @RequestBody LaneDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(laneService.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        laneService.delete(id);
        return ResponseEntity.ok().build();
    }
}
