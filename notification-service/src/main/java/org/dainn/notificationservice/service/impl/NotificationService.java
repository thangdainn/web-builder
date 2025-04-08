package org.dainn.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.notificationservice.dto.NotificationDetailDto;
import org.dainn.notificationservice.dto.NotificationDto;
import org.dainn.notificationservice.dto.NotificationRequest;
import org.dainn.notificationservice.dto.UserDto;
import org.dainn.notificationservice.mapper.INotificationMapper;
import org.dainn.notificationservice.model.Notification;
import org.dainn.notificationservice.repository.INotificationRepository;
import org.dainn.notificationservice.service.INotificationService;
import org.dainn.notificationservice.util.Paging;
import org.dainn.notificationservice.webclient.UserWC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {
    private final INotificationRepository notificationRepository;
    private final INotificationMapper notificationMapper;
    private final UserWC userClient;

    @Transactional
    @Override
    public Mono<NotificationDto> create(NotificationDto dto) {
        Notification notification = notificationMapper.toEntity(dto);
        notification.markNew();
        return notificationRepository.save(notification)
                .map(notificationMapper::toDto);
    }

    @Override
    public Mono<Page<NotificationDetailDto>> findByAgency(String agencyId, NotificationRequest request) {
        Pageable pageable = Paging.getPageable(request);
        Mono<Page<Notification>> response = notificationRepository.findByAgencyId(agencyId, pageable)
                .collectList()
                .zipWith(notificationRepository.count())
                .map(n -> new PageImpl<>(n.getT1(), pageable, n.getT2()));
        return response.flatMap(page -> {
            // Chuyển danh sách Notification thành Flux để xử lý từng phần tử
            return Flux.fromIterable(page.getContent())
                    .flatMap(n -> {
                        // Ánh xạ Notification thành NotificationDetailDto
                        NotificationDetailDto dto = notificationMapper.toDetailDto(n);
                        // Gọi UserWC để lấy UserDto (reactive)
                        return userClient.getById(n.getUserId())
                                .map(user -> {
                                    dto.setUser(user); // Gán UserDto vào NotificationDetailDto
                                    return dto;
                                })
                                .onErrorResume(error -> {
                                    dto.setUser(new UserDto("Unknown", "unknown", "unknown", "unknown", "unknown", "unknown"));
                                    return Mono.just(dto);
                                });
                    })
                    .collectList()
                    .map(dtos -> new PageImpl<>(dtos, pageable, page.getTotalElements()));
        });
    }
}
