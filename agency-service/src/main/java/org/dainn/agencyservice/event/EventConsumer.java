package org.dainn.agencyservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.agencyservice.dto.event.CustomerResponse;
import org.dainn.agencyservice.repository.IAgencyRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final ObjectMapper objectMapper;
    private final IAgencyRepository agencyRepository;

    @KafkaListener(topics = "create-customer-failed-events", groupId = "${spring.kafka.consumer.group-id}")
    public void handleCreateCusFailedEvent(@Payload String message) {
        log.info("Invite saved event consumed: {}", message);
        try {
            String email = objectMapper.readValue(message, String.class);
            // Process the customer object as needed
            log.info("Processing: {}", email);

        } catch (Exception e) {
            log.error("Failed to process create customer event", e);
        }
    }

    @KafkaListener(topics = "create-customer-success-events", groupId = "${spring.kafka.consumer.group-id}")
    public void handleCreateCusSuccessEvent(@Payload String message) {
        try {
            log.info("Saved event consumed: {}", message);
            CustomerResponse resp = objectMapper.readValue(message, CustomerResponse.class);
            // Process the customer object as needed
            agencyRepository.updateCustomerId(resp.getAgencyId(), resp.getCustomerId());
        } catch (Exception e) {
            log.error("Failed to process create customer event", e);
        }

    }
}