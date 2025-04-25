package org.dainn.pipelineservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.pipelineservice.repository.ITagRepository;
import org.dainn.pipelineservice.service.IPipelineService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final ObjectMapper objectMapper;
    private final IPipelineService pipelineService;
    private final ITagRepository tagRepository;

    @KafkaListener(topics = "subaccount-deleted-events", groupId = "${spring.kafka.consumer.group-id}")
    public void deleteSubAccount(@Payload String message) {
        try {
            String subAccountId = objectMapper.readValue(message, String.class);
            pipelineService.deleteBySA(subAccountId);
            tagRepository.deleteAllBySubAccountId(subAccountId);
            log.info("Sub-account {} deleted successfully", subAccountId);
        } catch (Exception e) {
            log.error("Failed to delete sub-account: {}", e.getMessage());
        }
    }
}