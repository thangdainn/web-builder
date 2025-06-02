package org.dainn.userservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.userservice.dto.event.DeleteAgencyEvent;
import org.dainn.userservice.service.IInvitationService;
import org.dainn.userservice.service.ISubAccountService;
import org.dainn.userservice.service.IUserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final ObjectMapper objectMapper;
    private final IInvitationService invitationService;
    private final IUserService userService;
    private final ISubAccountService subAccountService;

    @KafkaListener(topics = "send-email-failed-events", groupId = "${spring.kafka.consumer.group-id}")
    public void handleSendMailFailed(@Payload String message) {
        log.info("Invite email failed event consumed: {}", message);
        try {
            String inviteId = objectMapper.readValue(message, String.class);
            invitationService.deleteById(inviteId);
            log.info("Deleted invitation with ID: {} success", inviteId);
        } catch (Exception e) {
            log.error("Failed to process create customer event", e);
        }
    }

    @KafkaListener(topics = "change-permission-events", groupId = "${spring.kafka.consumer.group-id}")
    public void changePermission(@Payload String message) {
        log.info("User sync permission event consumed: {}", message);
        try {
            String email = objectMapper.readValue(message, String.class);
            userService.syncPermission(email);

            log.info("User sync permission for email: {} success", email);
        } catch (Exception e) {
            log.error("Failed to process user sync permission event", e);
        }
    }

    @KafkaListener(topics = "change-agency-events", groupId = "${spring.kafka.consumer.group-id}")
    public void changeAgency(@Payload String message) {
        log.info("User sync agency event consumed: {}", message);
        try {
            String email = objectMapper.readValue(message, String.class);
            userService.syncAgency(email);

            log.info("User sync agency for email: {} success", email);
        } catch (Exception e) {
            log.error("Failed to process user sync agency event: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "agency-deleted-events", groupId = "${spring.kafka.consumer.group-id}")
    public void deleteAgency(@Payload String message) {
        try {
            DeleteAgencyEvent dto = objectMapper.readValue(message, DeleteAgencyEvent.class);
            invitationService.deleteByAgencyId(dto.getAgencyId());
            userService.deleteByAgency(dto.getAgencyId());
            subAccountService.deleteByAgency(dto);
            log.info("Invite deleted by agency id {} successfully", dto.getAgencyId());
        } catch (Exception e) {
            log.error("Failed to delete invite: {}", e.getMessage());
        }
    }
}