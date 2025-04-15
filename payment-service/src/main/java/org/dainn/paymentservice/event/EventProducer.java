package org.dainn.paymentservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.model.Subscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.paymentservice.dto.event.CustomerResponse;
import org.dainn.paymentservice.dto.response.SubscriptionResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.payment-process-events}")
    private String paymentProcessEventsTopic;

    @Value("${kafka.topic.create-customer-success-events}")
    private String createCustomerSuccessEventsTopic;

    @Value("${kafka.topic.create-customer-failed-events}")
    private String createCustomerFailedEventsTopic;

    public void sendPaymentProcessEvent(Subscription subscription, String customerId) {
        try {
            SubscriptionResp dto = new SubscriptionResp();
            dto.setSubscriptionId(subscription.getId());
            dto.setCustomerId(customerId);
            dto.setActive(subscription.getStatus().equals("active"));
            dto.setAgencyId(subscription.getMetadata().get("agencyId"));
            LocalDate localDate = Instant.ofEpochSecond(subscription.getCurrentPeriodEnd())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            dto.setCurrentPeriodEndDate(localDate);
            dto.setPriceId(subscription.getMetadata().get("planId"));
            dto.setPlan(subscription.getMetadata().get("planName"));
            String jsonMessage = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send(paymentProcessEventsTopic, jsonMessage);
        } catch (Exception e) {
            log.error("Failed to serialize subscription to JSON");
        }
    }

    public void sendCreateCustomerSuccessEvent(String customerId, String agencyId) {
        try {
            CustomerResponse resp = new CustomerResponse(customerId, agencyId);
            String jsonMessage = objectMapper.writeValueAsString(resp);
            kafkaTemplate.send(createCustomerSuccessEventsTopic, jsonMessage);
        } catch (Exception e) {
            log.error("Failed to serialize customer to JSON", e);
        }
    }

    public void sendCreateCustomerFailedEvent(String email) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(email);
            kafkaTemplate.send(createCustomerFailedEventsTopic, jsonMessage);
        } catch (Exception e) {
            log.error("Failed to serialize customer to JSON", e);
        }
    }

}
