package org.dainn.funnelservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.funnelservice.service.IFunnelService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final ObjectMapper objectMapper;
    private final IFunnelService funnelService;

    @KafkaListener(topics = "subaccount-deleted-events", groupId = "${spring.kafka.consumer.group-id}")
    public void deleteSubAccount(@Payload String message) {
        try {
            String subAccountId = objectMapper.readValue(message, String.class);
            funnelService.deleteBySA(subAccountId)
                    .doOnSuccess(unused -> log.info("Deleted by sub-account {}", subAccountId))
                    .doOnError(e -> log.error("Failed to delete by sub-account {}: {}", subAccountId, e.getMessage()))
                    .onErrorResume(e -> {
                        // Handle error - can add logic retry or dead-letter queue
                        return Mono.empty();
                    })
                    .subscribe();
        } catch (Exception e) {
            log.error("Failed to process sub-account delete message: {}", e.getMessage());
        }
    }
}