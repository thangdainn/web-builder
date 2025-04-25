package org.dainn.userservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.userservice.repository.IInvitationRepository;
import org.dainn.userservice.service.IPermissionService;
import org.dainn.userservice.service.IUserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final ObjectMapper objectMapper;
    private final IInvitationRepository invitationRepository;
    private final IUserService userService;

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

    @KafkaListener(topics = "change-permission-events", groupId = "${spring.kafka.consumer.group-id}")
    public void changePermission(@Payload String message) {
        log.info("User sync permission event consumed: {}", message);
        try {
            Thread.sleep(500);
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
            Thread.sleep(500);
            String email = objectMapper.readValue(message, String.class);
            userService.syncAgency(email);

            log.info("User sync agency for email: {} success", email);
        } catch (Exception e) {
            log.error("Failed to process user sync agency event: {}", e.getMessage());
        }
    }

//    @KafkaListener(topics = "subaccount-deleted-events", groupId = "${spring.kafka.consumer.group-id}")
//    public void deleteSubAccount(@Payload String message) {
//        try {
//            String subAccountId = objectMapper.readValue(message, String.class);
//            permissionService.deleteBySA(subAccountId);
//            log.info("Sub-account {} deleted successfully", subAccountId);
//        } catch (Exception e) {
//            log.error("Failed to delete permission: {}", e.getMessage());
//        }
//    }

    @KafkaListener(topics = "agency-deleted-events", groupId = "${spring.kafka.consumer.group-id}")
    public void deleteAgency(@Payload String message) {
        try {
            String agencyId = objectMapper.readValue(message, String.class);
            invitationRepository.deleteAllByAgencyId(agencyId);
            userService.deleteByAgency(agencyId);
            log.info("Invite deleted by agency id {} successfully", agencyId);
        } catch (Exception e) {
            log.error("Failed to delete invite: {}", e.getMessage());
        }
    }
}