package org.dainn.pipelineservice.controller;


import lombok.RequiredArgsConstructor;
import org.dainn.pipelineservice.config.endpoint.Endpoint;
import org.dainn.pipelineservice.dto.ticket.TicketDto;
import org.dainn.pipelineservice.service.ITicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Ticket.BASE)
@RequiredArgsConstructor
public class TicketController {
    private final ITicketService ticketService;

    @GetMapping(Endpoint.Ticket.ID)
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(ticketService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TicketDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.create(dto));
    }

    @PutMapping(Endpoint.Ticket.ID)
    public ResponseEntity<?> updateOrder(@PathVariable String id, @RequestBody TicketDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(ticketService.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        ticketService.delete(id);
        return ResponseEntity.ok().build();
    }
}
