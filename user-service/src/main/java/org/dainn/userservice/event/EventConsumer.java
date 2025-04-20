package org.dainn.userservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.userservice.repository.IInvitationRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final ObjectMapper objectMapper;
    private final IInvitationRepository invitationRepository;

    @KafkaListener(topics = "send-email-failed-events", groupId = "${spring.kafka.consumer.group-id}")
    public void handleSendMailFailed(@Payload String message) {
        log.info("Invite email failed event consumed: {}", message);
        try {
            String inviteId = objectMapper.readValue(message, String.class);
            invitationRepository.deleteById(inviteId);
            log.info("Deleted invitation with ID: {} success", inviteId);
        } catch (Exception e) {
            log.error("Failed to process create customer event", e);
        }
    }
}