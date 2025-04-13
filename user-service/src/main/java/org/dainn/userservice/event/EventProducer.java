package org.dainn.userservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.userservice.dto.mail.MailData;
import org.dainn.userservice.dto.user.UserAccessProducer;
import org.dainn.userservice.dto.user.UserProducer;
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

    @Value("${kafka.topic.update-access-events}")
    private String updateAccessTopic;

    @Value("${kafka.topic.delete-events}")
    private String deleteEventsTopic;

    @Value("${kafka.topic.invite-events}")
    private String inviteEventsTopic;

    @Value("${kafka.topic.dead-letter}")
    private String deadLetterTopic;

    public void sendUserEvent(UserProducer dto) {
        String key = dto.getId();
        sendWithRetry(dto, key, createEventsTopic, 3, 1000);
    }

    public void sendUpdateAccessEvent(UserAccessProducer dto) {
        String key = dto.getUserId();
        sendWithRetry(dto, key, updateAccessTopic, 3, 1000);
    }

    public void sendInviteEvent(MailData dto) {
        String key = dto.getTo();
        sendWithRetry(dto, key, inviteEventsTopic, 3, 1000);
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
//                        sendToDeadLetterQueue(dto);
                    }
                }
            });
        } catch (Exception e) {
            log.error("Failed to serialize NotificationDto to JSON: {}", dto, e);
//            sendToDeadLetterQueue(dto);
        }
    }
//
//    private <T> void sendToDeadLetterQueue(T dto) {
//        if (dto == null) {
//            log.warn("Cannot send null dto to dead-letter queue");
//            return;
//        }
//        String key = dto.getId();
//        try {
//            String jsonMessage = objectMapper.writeValueAsString(dto);
//            CompletableFuture<?> future = kafkaTemplate.send(deadLetterTopic, key, jsonMessage);
//            future.whenComplete((result, ex) -> {
//                if (ex == null) {
//                    log.info("Sent failed user to dead-letter topic {}: {}", deadLetterTopic, jsonMessage);
//                } else {
//                    log.error("Failed to send user to dead-letter topic {}: {}", deadLetterTopic, ex.getMessage());
//                }
//            });
//        } catch (Exception e) {
//            log.error("Failed to serialize user to JSON for dead-letter queue: {}", dto, e);
//        }
//    }
}
