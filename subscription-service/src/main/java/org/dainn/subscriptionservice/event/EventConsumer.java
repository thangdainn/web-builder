package org.dainn.subscriptionservice.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.subscriptionservice.dto.event.SubscriptionResp;
import org.dainn.subscriptionservice.mapper.ISubscriptionMapper;
import org.dainn.subscriptionservice.repository.IAddOnsRepository;
import org.dainn.subscriptionservice.repository.ISubscriptionRepository;
import org.dainn.subscriptionservice.service.ISubscriptionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final ObjectMapper objectMapper;
    private final ISubscriptionMapper subscriptionMapper;
    private final ISubscriptionService subscriptionService;
    private final ISubscriptionRepository subscriptionRepository;
    private final IAddOnsRepository addOnsRepository;

    @KafkaListener(topics = "payment-processed-events", groupId = "subscription-service-group")
    public void handleEvents(@Payload String message) throws JsonProcessingException {
        SubscriptionResp event = objectMapper.readValue(message, SubscriptionResp.class);
        log.info("Received event from topic payment-processed-events: {}", event);
        subscriptionService.findByAgencyIdEvent(event.getAgencyId())
                .flatMap(existing -> {
                    existing = subscriptionMapper.toUpdate(existing, event);
                    return subscriptionService.update(existing);
                })
                .switchIfEmpty(subscriptionService.create(subscriptionMapper.toDto(event)))
                .subscribe();
    }
    @KafkaListener(topics = "agency-deleted-events", groupId = "subscription-service-group")
    public void handleAgencyDeletedEvents(@Payload String message) throws JsonProcessingException {
        String agencyId = objectMapper.readValue(message, String.class);
        subscriptionRepository.deleteByAgencyId(agencyId).subscribe();
        addOnsRepository.deleteByAgencyId(agencyId).subscribe();
    }
}