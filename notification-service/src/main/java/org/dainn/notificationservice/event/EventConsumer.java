package org.dainn.notificationservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.notificationservice.dto.NotificationDto;
import org.dainn.notificationservice.service.impl.NotificationService;
import org.dainn.notificationservice.webclient.SubAccountWC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;
    private final SubAccountWC subAccountClient;
//    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "media-events", groupId = "notification-service-group")
    public void handleMediaEvent(@Payload String rawJson) {
        try {
            NotificationDto dto = objectMapper.readValue(rawJson, NotificationDto.class);
            if (dto.getSubAccountId() != null) {
                getSubAccount(dto)
                        .doOnNext(n -> log.info("Enriched notification: {}", n))
                        .subscribe();
            } else {
                log.warn("SubAccountId is null, skipping enrichment for notification: {}", dto);
            }

            Mono.just(dto)
                    .doOnNext(n -> log.info("Received media notification: {}", n))
                    .flatMap(this::processMessage)
                    .subscribeOn(Schedulers.boundedElastic())
                    .subscribe();

        } catch (Exception e) {
            log.error("Failed to process message: {}", rawJson, e);
        }
    }

    private Mono<Void> processMessage(NotificationDto dto) {
        return notificationService.create(dto)
                .doOnNext(n -> {
                    log.info("Notification saved: {}", n.getId());
//                    sendWebSocketNotification(n);
                })
                .then();
    }

    private Mono<NotificationDto> getSubAccount(NotificationDto dto) {
        return subAccountClient.getById(dto.getSubAccountId())
                .map(subAccount -> {
                    dto.setAgencyId(subAccount.getAgencyId());
                    return dto;
                })
                .doOnError(e -> log.error("Failed to enrich notification with sub account: {}", dto, e))
                .thenReturn(dto);
    }

//    private void sendWebSocketNotification(NotificationDto dto) {
//        // Gửi thông báo tới tất cả client subscribe vào /topic/notifications
//        messagingTemplate.convertAndSend("/topic/notifications", dto);
//
//        // (Tùy chọn) Gửi thông báo tới một user cụ thể (dựa trên userId)
//        messagingTemplate.convertAndSendToUser(
//                dto.getAgencyId(),
//                "/topic/notifications",
//                dto
//        );
//    }
}
