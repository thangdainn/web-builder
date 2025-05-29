package org.dainn.funnelservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.funnelservice.dto.event.FPOrderEvent;
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

    @Value("${kafka.topic.change-fp-order-events}")
    private String changeFPTopic;

    public void changeFPOrderEvent(FPOrderEvent dto) {
        sendWithRetry(dto, dto.getFunnelId(), changeFPTopic, 3, 1000);
    }

    private <T> void sendWithRetry(T dto, String key, String topic, int maxRetries, long delay) {
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
                    }
                }
            });
        } catch (Exception e) {
            log.error("Failed to serialize NotificationDto to JSON: {}", dto);
        }
    }
}
