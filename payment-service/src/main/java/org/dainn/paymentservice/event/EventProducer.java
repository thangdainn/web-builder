package org.dainn.paymentservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.model.Subscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.paymentservice.dto.event.CustomerResponse;
import org.dainn.paymentservice.dto.response.AgencyDto;
import org.dainn.paymentservice.dto.response.SubscriptionResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public void sendPaymentProcessEvent(Subscription subscription, String customerId, AgencyDto agency) {
        try {
            SubscriptionResp dto = new SubscriptionResp();
            dto.setSubscriptionId(subscription.getId());
            dto.setCustomerId(customerId);
            dto.setActive(subscription.getStatus().equals("active"));
            dto.setAgencyId(agency.getId());
            LocalDate localDate = Instant.ofEpochSecond(subscription.getCurrentPeriodEnd())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            dto.setCurrentPeriodEndDate(localDate);
            String plant = subscription.getItems().getData().get(0).getPrice().getId();
            dto.setPriceId(plant);
            dto.setPrice(subscription.getItems().getData().get(0).getPrice().getUnitAmountDecimal().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            dto.setPlan(plant);
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
