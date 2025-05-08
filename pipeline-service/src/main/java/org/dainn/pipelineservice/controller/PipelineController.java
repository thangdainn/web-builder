package org.dainn.pipelineservice.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.dainn.pipelineservice.config.endpoint.Endpoint;
import org.dainn.pipelineservice.dto.lane.LaneOrderDto;
import org.dainn.pipelineservice.dto.pipeline.PipelineDto;
import org.dainn.pipelineservice.dto.ticket.TicketOrderList;
import org.dainn.pipelineservice.service.ILaneService;
import org.dainn.pipelineservice.service.IPipelineService;
import org.dainn.pipelineservice.service.ITicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.Pipeline.BASE)
@RequiredArgsConstructor
public class PipelineController {
    private final IPipelineService pipelineService;
    private final ILaneService laneService;
    private final ITicketService ticketService;

    @GetMapping(Endpoint.Pipeline.SA)
    public ResponseEntity<?> getBySA(@PathVariable String id) {
        return ResponseEntity.ok(pipelineService.findBySA(id));
    }

    @GetMapping(Endpoint.Pipeline.ID)
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(pipelineService.findById(id));
    }

    @GetMapping(Endpoint.Pipeline.LANE)
    public ResponseEntity<?> getLane(@PathVariable String id) {
        return ResponseEntity.ok(laneService.findByPipeline(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PipelineDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pipelineService.create(dto));
    }

    @PutMapping(Endpoint.Pipeline.LANE_ORDER)
    public ResponseEntity<?> changeLaneOrder(@PathVariable String id, @RequestBody List<LaneOrderDto> list) {
        laneService.changeOrderWithKafka(id, list);
        return ResponseEntity.ok().build();
    }

    @PutMapping(Endpoint.Pipeline.TICKET_ORDER)
    public ResponseEntity<?> changeTicketOrder(@RequestBody @Size(max = 2, message = "List cannot contain more than 2 items") List<TicketOrderList> list) {
        ticketService.changeOrderWithKafka(list);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        pipelineService.delete(id);
        return ResponseEntity.ok().build();
    }
}
