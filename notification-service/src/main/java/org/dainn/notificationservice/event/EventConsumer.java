package org.dainn.notificationservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.dainn.notificationservice.dto.NotificationDto;
import org.dainn.notificationservice.service.impl.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.dead-letter}")
    private String deadLetterTopic;

    @KafkaListener(
            topics = {"create-events", "update-events", "delete-events"},
            groupId = "${spring.kafka.consumer.group-id:notification-service-group}",
            concurrency = "${spring.kafka.listener.concurrency:3}"
    )
    public void handleEvents(List<ConsumerRecord<String, String>> records) {
        log.info("Received batch of {} events from Kafka", records.size());

        Flux.fromIterable(records)
                .flatMap(record -> processEvent(record)
                        .onErrorResume(error -> {
                            log.error("Failed to process event from topic {}: {}", record.topic(), record.value(), error);
                            return sendToDeadLetterQueue(record);
                        }), 10)
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }

    private Mono<Void> processEvent(ConsumerRecord<String, String> record) {
        return Mono.fromCallable(() -> objectMapper.readValue(record.value(), NotificationDto.class))
                .doOnNext(dto -> log.info("Received event from topic {}: {}", record.topic(), dto))
                .flatMap(this::processMessage)
                .doOnError(error -> log.error("Error processing event from topic {}: {}", record.topic(), record.value(), error));
    }

    private Mono<Void> processMessage(NotificationDto dto) {
        return notificationService.create(dto)
                .doOnNext(savedDto -> {
                    log.info("Notification saved: {}", savedDto.getId());
                    sendWebSocketNotification(savedDto);
                })
                .then();
    }

    private void sendWebSocketNotification(NotificationDto dto) {
        messagingTemplate.convertAndSendToUser(
                dto.getAgencyId(),
                "/topic/notifications",
                dto
        );
    }

    private Mono<Void> sendToDeadLetterQueue(ConsumerRecord<String, String> record) {
        return Mono.fromRunnable(() -> {
            kafkaTemplate.send(deadLetterTopic, record.key(), record.value())
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Sent failed event to dead-letter topic {}: {}", deadLetterTopic, record.value());
                        } else {
                            log.error("Failed to send event to dead-letter topic {}: {}", deadLetterTopic, ex.getMessage());
                        }
                    });
        });
    }

}