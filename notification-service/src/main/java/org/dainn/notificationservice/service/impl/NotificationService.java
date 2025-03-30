package org.dainn.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.notificationservice.dto.NotificationDto;
import org.dainn.notificationservice.dto.NotificationRequest;
import org.dainn.notificationservice.mapper.INotificationMapper;
import org.dainn.notificationservice.model.Notification;
import org.dainn.notificationservice.repository.INotificationRepository;
import org.dainn.notificationservice.service.INotificationService;
import org.dainn.notificationservice.util.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {
    private final INotificationRepository notificationRepository;
    private final INotificationMapper notificationMapper;

    @Override
    public Mono<NotificationDto> create(NotificationDto dto) {
        Notification notification = notificationMapper.toEntity(dto);
        notification.markNew();
        return notificationRepository.save(notification)
                .map(notificationMapper::toDto);
    }

    @Override
    public Mono<Page<NotificationDto>> findByUserAndAgency(NotificationRequest request) {
        Pageable pageable = Paging.getPageable(request);
        Mono<Page<Notification>> response =notificationRepository.findByUserIdAndAgencyId(request.getUserId(), request.getAgencyId(), pageable)
                .collectList()
                .zipWith(notificationRepository.count())
                .map(n -> new PageImpl<>(n.getT1(), pageable, n.getT2()));
        return response.map(page -> page.map(notificationMapper::toDto));
    }

    @Override
    public Mono<Page<NotificationDto>> findBySubAccountAndAgency(NotificationRequest request) {
        Pageable pageable = Paging.getPageable(request);
        Mono<Page<Notification>> response =notificationRepository.findBySubAccountIdAndAgencyId(request.getUserId(), request.getAgencyId(), pageable)
                .collectList()
                .zipWith(notificationRepository.count())
                .map(n -> new PageImpl<>(n.getT1(), pageable, n.getT2()));
        return response.map(page -> page.map(notificationMapper::toDto));
    }
}
