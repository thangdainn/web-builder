package org.dainn.subscriptionservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.subscriptionservice.config.endpoint.Endpoint;
import org.dainn.subscriptionservice.dto.SubscriptionDto;
import org.dainn.subscriptionservice.service.ISubscriptionService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(Endpoint.Subscription.BASE)
@RequiredArgsConstructor
public class SubscriptionController {
    private final ISubscriptionService subscriptionService;

//    @GetMapping
//    public Mono<?> getAll() {
//        return subscriptionService.findAll();
//    }

    @GetMapping(Endpoint.Subscription.ID)
    public Mono<SubscriptionDto> get(@PathVariable String id) {
        return subscriptionService.findById(id);
    }

    @PostMapping
    public Mono<SubscriptionDto> create(@Valid @RequestBody SubscriptionDto dto) {
        return subscriptionService.create(dto);
    }

    @PutMapping
    public Mono<SubscriptionDto> update(@RequestBody SubscriptionDto dto) {
        return subscriptionService.update(dto);
    }

    @DeleteMapping
    public Mono<Void> delete(String id) {
        subscriptionService.delete(id);
        return Mono.empty();
    }
}
