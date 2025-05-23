package org.dainn.subaccountservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.subaccountservice.service.ISubAccountService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final ObjectMapper objectMapper;
    private final ISubAccountService subAccountService;

    @KafkaListener(topics = "agency-deleted-events", groupId = "${spring.kafka.consumer.group-id}")
    public void deleteAgency(@Payload String message) {
        try {
            String agencyId = objectMapper.readValue(message, String.class);
            subAccountService.deleteByAgency(agencyId);
            log.info("Invite deleted by agency id {} successfully", agencyId);
        } catch (Exception e) {
            log.error("Failed to delete invite: {}", e.getMessage());
        }
    }
}