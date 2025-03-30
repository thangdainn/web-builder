package org.dainn.notificationservice.service;


import org.dainn.notificationservice.dto.NotificationDto;
import org.dainn.notificationservice.dto.NotificationRequest;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

public interface INotificationService {
    Mono<NotificationDto> create(NotificationDto dto);
    Mono<Page<NotificationDto>> findByUserAndAgency(NotificationRequest request);
    Mono<Page<NotificationDto>> findBySubAccountAndAgency(NotificationRequest request);
}
