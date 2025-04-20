package org.dainn.notificationservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.notificationservice.dto.NotificationDto;
import org.dainn.notificationservice.dto.mail.MailData;
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

    @Value("${kafka.topic.send-email-failed-events}")
    private String sendEmailFailedEventsTopic;

    public void sendNotification(NotificationDto dto) {
        if (dto == null || dto.getAction() == null) {
            log.error("Invalid notification: action is null or dto is null: {}", dto);
            return;
        }

        String topic = determineTopic(dto.getAction());
        String key = dto.getUserId();
        sendWithRetry(dto, key, topic, 3, 1000);
    }

    public void sendEmailFailed(String inviteId) {
        String topic = sendEmailFailedEventsTopic;
        sendWithRetry(inviteId, inviteId, topic, 3, 1000);
    }

    private String determineTopic(String action) {
        return switch (action.toUpperCase()) {
            case "CREATE" -> createEventsTopic;
            case "UPDATE" -> updateEventsTopic;
            case "DELETE" -> deleteEventsTopic;
            default -> createEventsTopic;
        };
    }

    private <T> void sendWithRetry(T dto, String key, String topic, int maxRetries, long delay) {
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
                    }
                }
            });
        } catch (Exception e) {
            log.error("Failed to serialize NotificationDto to JSON: {}", dto, e);
        }
    }
}
