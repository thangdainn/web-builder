package org.dainn.pipelineservice.controller;


import lombok.RequiredArgsConstructor;
import org.dainn.pipelineservice.config.endpoint.Endpoint;
import org.dainn.pipelineservice.dto.TicketDto;
import org.dainn.pipelineservice.service.ITicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Tickets.BASE)
@RequiredArgsConstructor
public class TicketController {
    private final ITicketService ticketService;

    @GetMapping(Endpoint.Tickets.ID)
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(ticketService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TicketDto dto) {
        return ResponseEntity.ok(ticketService.create(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        ticketService.delete(id);
        return ResponseEntity.ok().build();
    }
}
