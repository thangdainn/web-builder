package org.dainn.pipelineservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.pipelineservice.dto.event.LaneOrderEvent;
import org.dainn.pipelineservice.dto.event.TicketOrderEvent;
import org.dainn.pipelineservice.repository.ITagRepository;
import org.dainn.pipelineservice.service.ILaneService;
import org.dainn.pipelineservice.service.IPipelineService;
import org.dainn.pipelineservice.service.ITicketService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final ObjectMapper objectMapper;
    private final IPipelineService pipelineService;
    private final ILaneService laneService;
    private final ITicketService ticketService;
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

    @KafkaListener(topics = "change-lane-order-event", groupId = "${spring.kafka.consumer.group-id}")
    public void changeLaneOrder(@Payload String message) {
        try {
            LaneOrderEvent data = objectMapper.readValue(message, LaneOrderEvent.class);
            laneService.changeOrder(data);
            log.info("Change lane order event successfully for pipeline {}", data.getPipelineId());
        } catch (Exception e) {
            log.error("Failed to change lane order event: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "change-ticket-order-event", groupId = "${spring.kafka.consumer.group-id}")
    public void changeTicketOrder(@Payload String message) {
        try {
            TicketOrderEvent data = objectMapper.readValue(message , TicketOrderEvent.class);
            ticketService.changeOrder(data);
            log.info("Change lane order event successfully");
        } catch (Exception e) {
            log.error("Failed to change ticket order event: {}", e.getMessage());
        }
    }
}