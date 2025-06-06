package org.dainn.agencyservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.agencyservice.dto.CreateCustomer;
import org.dainn.agencyservice.dto.DeleteAgencyDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.create-customer-events}")
    private String createCustomerTopic;

    @Value("${kafka.topic.change-agency-events}")
    private String changeAgencyTopic;

    @Value("${kafka.topic.agency-deleted-events}")
    private String agencyDeletedEventsTopic;

    public void sendCreateCustomerEvent(CreateCustomer customer) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(customer);
            kafkaTemplate.send(createCustomerTopic, jsonMessage);
        } catch (Exception e) {
            log.error("Failed to serialize customer to JSON", e);
        }
    }

    public void changeAgencyEvent(String email) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(email);
            kafkaTemplate.send(changeAgencyTopic, jsonMessage);
        } catch (Exception e) {
            log.error("Failed to serialize user to JSON", e);
        }
    }

    public void agencyDeletedEvent(DeleteAgencyDto dto) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send(agencyDeletedEventsTopic, jsonMessage);
        } catch (Exception e) {
            log.error("Failed to serialize user to JSON", e);
        }
    }
}
