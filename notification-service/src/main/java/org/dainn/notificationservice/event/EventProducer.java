package org.dainn.notificationservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.notificationservice.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.create-events}")
    private String createEventsTopic;

    @Value("${kafka.topic.update-events}")
    private String updateEventsTopic;

    @Value("${kafka.topic.delete-events}")
    private String deleteEventsTopic;

    @Value("${kafka.topic.dead-letter}")
    private String deadLetterTopic;

    public void sendNotification(NotificationDto dto) {
        if (dto == null || dto.getAction() == null) {
            log.error("Invalid notification: action is null or dto is null: {}", dto);
            sendToDeadLetterQueue(dto);
            return;
        }

        String topic = determineTopic(dto.getAction());
        String key = dto.getUserId();
        sendWithRetry(dto, key, topic, 3, 1000); // Retry 3 lần, delay 1 giây
    }

    private String determineTopic(String action) {
        return switch (action.toUpperCase()) {
            case "CREATE" -> createEventsTopic;
            case "UPDATE" -> updateEventsTopic;
            case "DELETE" -> deleteEventsTopic;
            default -> {
                log.warn("Unknown action: {}, sending to dead-letter topic", action);
                yield deadLetterTopic;
            }
        };
    }

    private void sendWithRetry(NotificationDto dto, String key, String topic, int maxRetries, long delay) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(dto);
            CompletableFuture<?> future = kafkaTemplate.send(topic, key, jsonMessage);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Sent notification to Kafka topic {} with key {}: {}", topic, key, jsonMessage);
                } else {
                    if (maxRetries > 0) {
                        log.warn("Failed to send notification to topic {}, retrying... (remaining retries: {})", topic, maxRetries);
                        try {
                            Thread.sleep(delay);
                            sendWithRetry(dto, key, topic, maxRetries - 1, delay);
                        } catch (InterruptedException e) {
                            log.error("Retry interrupted: {}", e.getMessage());
                            Thread.currentThread().interrupt();
                        }
                    } else {
                        log.error("Failed to send notification to topic {} after retries: {}", topic, ex.getMessage());
                        sendToDeadLetterQueue(dto);
                    }
                }
            });
        } catch (Exception e) {
            log.error("Failed to serialize NotificationDto to JSON: {}", dto, e);
            sendToDeadLetterQueue(dto);
        }
    }

    private void sendToDeadLetterQueue(NotificationDto dto) {
        if (dto == null) {
            log.warn("Cannot send null dto to dead-letter queue");
            return;
        }
        String key = dto.getUserId() != null ? dto.getUserId() : dto.getId();
        try {
            String jsonMessage = objectMapper.writeValueAsString(dto);
            CompletableFuture<?> future = kafkaTemplate.send(deadLetterTopic, key, jsonMessage);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Sent failed notification to dead-letter topic {}: {}", deadLetterTopic, jsonMessage);
                } else {
                    log.error("Failed to send notification to dead-letter topic {}: {}", deadLetterTopic, ex.getMessage());
                }
            });
        } catch (Exception e) {
            log.error("Failed to serialize NotificationDto to JSON for dead-letter queue: {}", dto, e);
        }
    }
}
