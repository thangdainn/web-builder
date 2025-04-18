package org.dainn.subaccountservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.subaccountservice.dto.UserProducer;
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

    @Value("${kafka.topic.update-events}")
    private String updateEventsTopic;

    @Value("${kafka.topic.delete-events}")
    private String deleteEventsTopic;

    @Value("${kafka.topic.dead-letter}")
    private String deadLetterTopic;

    public void sendUserEvent(UserProducer dto) {
        String key = dto.getId();
        sendWithRetry(dto, key, updateEventsTopic, 3, 1000);
    }

    private void sendWithRetry(UserProducer dto, String key, String topic, int maxRetries, long delay) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(dto);
            CompletableFuture<?> future = kafkaTemplate.send(topic, key, jsonMessage);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Sent user to Kafka topic {} with key {}: {}", topic, key, jsonMessage);
                } else {
                    if (maxRetries > 0) {
                        log.warn("Failed to send user to topic {}, retrying... (remaining retries: {})", topic, maxRetries);
                        try {
                            Thread.sleep(delay);
                            sendWithRetry(dto, key, topic, maxRetries - 1, delay);
                        } catch (InterruptedException e) {
                            log.error("Retry interrupted: {}", e.getMessage());
                            Thread.currentThread().interrupt();
                        }
                    } else {
                        log.error("Failed to send user to topic {} after retries: {}", topic, ex.getMessage());
                        sendToDeadLetterQueue(dto);
                    }
                }
            });
        } catch (Exception e) {
            log.error("Failed to serialize NotificationDto to JSON: {}", dto, e);
            sendToDeadLetterQueue(dto);
        }
    }

    private void sendToDeadLetterQueue(UserProducer dto) {
        if (dto == null) {
            log.warn("Cannot send null dto to dead-letter queue");
            return;
        }
        String key = dto.getId();
        try {
            String jsonMessage = objectMapper.writeValueAsString(dto);
            CompletableFuture<?> future = kafkaTemplate.send(deadLetterTopic, key, jsonMessage);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Sent failed user to dead-letter topic {}: {}", deadLetterTopic, jsonMessage);
                } else {
                    log.error("Failed to send user to dead-letter topic {}: {}", deadLetterTopic, ex.getMessage());
                }
            });
        } catch (Exception e) {
            log.error("Failed to serialize user to JSON for dead-letter queue: {}", dto, e);
        }
    }
}
