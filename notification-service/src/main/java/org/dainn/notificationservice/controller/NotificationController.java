package org.dainn.notificationservice.controller;


import lombok.RequiredArgsConstructor;
import org.dainn.notificationservice.config.endpoint.Endpoint;
import org.dainn.notificationservice.dto.NotificationDto;
import org.dainn.notificationservice.dto.NotificationRequest;
import org.dainn.notificationservice.service.INotificationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(Endpoint.Notification.BASE)
@RequiredArgsConstructor
public class NotificationController {
    private final INotificationService notificationService;

    @GetMapping(Endpoint.Notification.userId)
    public ResponseEntity<Mono<Page<NotificationDto>>> getAllByUserAndAgency(@ModelAttribute NotificationRequest request) {
        return ResponseEntity.ok(notificationService.findByUserAndAgency(request));
    }

    @GetMapping(Endpoint.Notification.subAccountId)
    public ResponseEntity<Mono<Page<NotificationDto>>> getAllBySubAccountAndAgency(@ModelAttribute NotificationRequest request) {
        return ResponseEntity.ok(notificationService.findBySubAccountAndAgency(request));
    }

    @PostMapping
    public ResponseEntity<Mono<NotificationDto>> create(@RequestBody NotificationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.create(dto));
    }
}
