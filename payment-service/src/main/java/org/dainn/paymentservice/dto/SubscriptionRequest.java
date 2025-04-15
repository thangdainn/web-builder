package org.dainn.paymentservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionRequest {
    @NotNull(message = "CustomerId is required")
    @NotBlank(message = "CustomerId is required")
    private String customerId;

    @NotNull(message = "PriceId is required")
    @NotBlank(message = "PriceId is required")
    private String priceId;
}
