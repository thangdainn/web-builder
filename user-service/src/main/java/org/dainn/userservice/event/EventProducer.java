package org.dainn.userservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.userservice.dto.event.DeleteAgencyEvent;
import org.dainn.userservice.dto.event.SyncUser;
import org.dainn.userservice.dto.event.UserProducer;
import org.dainn.userservice.dto.mail.MailData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.sync-permission-events}")
    private String syncPermissionTopic;

    @Value("${kafka.topic.sync-agency-events}")
    private String syncAgencyTopic;

    @Value("${kafka.topic.sync-user-events}")
    private String syncUserTopic;

    @Value("${kafka.topic.sync-users-events}")
    private String syncUsersTopic;

    @Value("${kafka.topic.change-permission-events}")
    private String changePermissionTopic;

    @Value("${kafka.topic.change-agency-events}")
    private String changeAgencyTopic;

    @Value("${kafka.topic.user-delete-events}")
    private String deleteUserTopic;

    @Value("${kafka.topic.owner-delete-events}")
    private String deleteUserOwnerTopic;

    @Value("${kafka.topic.invite-events}")
    private String inviteEventsTopic;

    @Value("${kafka.topic.subaccount-deleted-events}")
    private String subAccountDeletedTopic;

    public void subAccountDeletedEvent(String subAccountId) {
        sendWithRetry(subAccountId, subAccountId, subAccountDeletedTopic, 3, 1000);
    }

    public void changePerEvent(String email) {
        sendWithRetry(email, email, changePermissionTopic, 3, 1000);
    }

    public void changeAgencyEvent(String email) {
        sendWithRetry(email, email, changeAgencyTopic, 3, 1000);
    }

//    public void syncPerEvent(UserProducer dto) {
//        String key = dto.getId();
//        sendWithRetry(dto, key, syncPermissionTopic, 3, 1000);
//    }
//
//    public void syncAgencyEvent(UserProducer dto) {
//        String key = dto.getId();
//        sendWithRetry(dto, key, syncAgencyTopic, 3, 1000);
//    }

    public void syncUserEvent(SyncUser dto) {
        String key = dto.getUser().getId();
        sendWithRetry(dto, key, syncUserTopic, 3, 1000);
    }

    public void syncUsersEvent(List<UserProducer> list) {
        String key = list.get(0).getId();
        sendWithRetry(list, key, syncUsersTopic, 3, 1000);
    }

    public void sendDeleteUserOwner(DeleteAgencyEvent dto) {
        sendWithRetry(dto, dto.getAgencyId(), deleteUserOwnerTopic, 3, 1000);
    }

    public void sendDeleteUser(String id) {
        sendWithRetry(id, id, deleteUserTopic, 3, 1000);
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
                    }
                }
            });
        } catch (Exception e) {
            log.error("Failed to serialize NotificationDto to JSON: {}", dto);
        }
    }
}
