package org.dainn.searchservice.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.searchservice.document.User;
import org.dainn.searchservice.service.IUserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final IUserService userService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "user-created-events", groupId = "${spring.kafka.consumer.group-id}")
    public void userCreatedEvent(@Payload String message) {
        try {
            log.info("User created event consumed: {}", message);
            User user = objectMapper.readValue(message, User.class);
            userService.create(user);
        } catch (JsonProcessingException e) {
            log.error("Error processing user create event: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "user-deleted-events", groupId = "${spring.kafka.consumer.group-id}")
    public void userDeletedEvent(@Payload String message) {
        try {
            log.info("User deleted event consumed: {}", message);
            String id = objectMapper.readValue(message, String.class);
            userService.delete(id);
        } catch (JsonProcessingException e) {
            log.error("Error processing user delete event: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "sync-permission-events", groupId = "${spring.kafka.consumer.group-id}")
    public void handleUserSyncPermission(@Payload String message) {
        log.info("User sync permission event consumed: {}", message);
        try {
            User user = objectMapper.readValue(message, User.class);
            userService.syncPermission(user);
        } catch (Exception e) {
            log.error("Failed to process user sync permission event", e);
        }
    }

    @KafkaListener(topics = "sync-agency-events", groupId = "${spring.kafka.consumer.group-id}")
    public void handleUserSyncAgency(@Payload String message) {
        log.info("User sync agency event consumed: {}", message);
        try {
            User user = objectMapper.readValue(message, User.class);
            userService.syncAgency(user);
        } catch (Exception e) {
            log.error("Failed to process user sync agency event", e);
        }
    }

    @KafkaListener(topics = "sync-user-events", groupId = "${spring.kafka.consumer.group-id}")
    public void handleUserSync(@Payload String message) {
        log.info("User sync agency event consumed: {}", message);
        try {
            List<User> users = objectMapper.readValue(message, new TypeReference<>() {});
            userService.sync(users);
        } catch (Exception e) {
            log.error("Failed to process user sync agency event", e);
        }
    }
}