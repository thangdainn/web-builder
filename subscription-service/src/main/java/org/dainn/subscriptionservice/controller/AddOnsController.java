package org.dainn.subscriptionservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.subscriptionservice.config.endpoint.Endpoint;
import org.dainn.subscriptionservice.dto.AddOnsDto;
import org.dainn.subscriptionservice.service.IAddOnsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(Endpoint.AddOns.BASE)
@RequiredArgsConstructor
public class AddOnsController {
    private final IAddOnsService addOnsService;

    @GetMapping(Endpoint.AddOns.ID)
    public ResponseEntity<Mono<AddOnsDto>> get(@PathVariable String id) {
        return ResponseEntity.ok(addOnsService.findById(id));
    }

//    @PostMapping
//    public ResponseEntity<Mono<AddOnsDto>> create(@Valid @RequestBody AddOnsDto dto) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(addOnsService.create(dto));
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Mono<Void>> delete(String id) {
//        return ResponseEntity.ok(addOnsService.delete(id));
//    }
}
