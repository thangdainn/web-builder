package org.dainn.paymentservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.paymentservice.dto.CreateCustomer;
import org.dainn.paymentservice.dto.CreateCustomerResp;
import org.dainn.paymentservice.service.impl.StripeService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final ObjectMapper objectMapper;
    private final StripeService stripeService;
    private final EventProducer eventProducer;

    @KafkaListener(topics = "create-customer-events",groupId = "payment-service-group")
    public void createCustomerEvent(@Payload String message) {
        log.info("Invite saved event consumed: {}", message);
        try {
            CreateCustomer customer = objectMapper.readValue(message, CreateCustomer.class);
            // Process the customer object as needed
            log.info("Processed customer: {}", customer);
            CreateCustomerResp resp = stripeService.createCustomer(customer);
            eventProducer.sendCreateCustomerSuccessEvent(resp.getCustomerId(), customer.getAgencyId());
        } catch (Exception e) {
            log.error("Failed to process create customer event", e);
            eventProducer.sendCreateCustomerFailedEvent(message);
        }
    }
}