package org.dainn.subscriptionservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.subscriptionservice.config.endpoint.Endpoint;
import org.dainn.subscriptionservice.dto.SubscriptionDto;
import org.dainn.subscriptionservice.service.ISubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Subscription.BASE)
@RequiredArgsConstructor
public class SubscriptionController {
    private final ISubscriptionService subscriptionService;

//    @GetMapping
//    public Mono<?> getAll() {
//        return subscriptionService.findAll();
//    }

//    @GetMapping(Endpoint.Subscription.ID)
//    public ResponseEntity<?> get(@PathVariable String id) {
//        return ResponseEntity.ok(subscriptionService.findById(id));
//    }

    @GetMapping(Endpoint.Subscription.AGENCY)
    public ResponseEntity<?> getByAgency(@PathVariable String id) {
        return ResponseEntity.ok(subscriptionService.findByAgencyId(id));
    }

//    @PostMapping
//    public ResponseEntity<?> create(@Valid @RequestBody SubscriptionDto dto) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionService.create(dto));
//    }

//    @PutMapping
//    public ResponseEntity<?> update(@RequestBody SubscriptionDto dto) {
//        return ResponseEntity.ok(subscriptionService.update(dto));
//    }

//    @DeleteMapping
//    public ResponseEntity<?> delete(String id) {
//        return ResponseEntity.ok(subscriptionService.delete(id));
//    }
}
