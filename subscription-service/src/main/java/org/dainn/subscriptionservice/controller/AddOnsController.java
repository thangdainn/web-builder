package org.dainn.subscriptionservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.subscriptionservice.config.endpoint.Endpoint;
import org.dainn.subscriptionservice.dto.AddOnsDto;
import org.dainn.subscriptionservice.service.IAddOnsService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(Endpoint.AddOns.BASE)
@RequiredArgsConstructor
public class AddOnsController {
    private final IAddOnsService addOnsService;

    @GetMapping(Endpoint.AddOns.ID)
    public Mono<AddOnsDto> get(@PathVariable String id) {
        return addOnsService.findById(id);
    }

    @PostMapping
    public Mono<AddOnsDto> create(@Valid @RequestBody AddOnsDto dto) {
        return addOnsService.create(dto);
    }

    @DeleteMapping
    public Mono<Void> delete(String id) {
        addOnsService.delete(id);
        return Mono.empty();
    }
}
