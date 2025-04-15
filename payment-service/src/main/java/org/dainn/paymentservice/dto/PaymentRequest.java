package org.dainn.paymentservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Builder
public class PaymentRequest {

    @NotNull(message = "Stripe Account Id is required")
    @NotBlank(message = "Stripe Account Id is required")
    private String subAccountConnectAccId;

    @NotEmpty(message = "Prices is required")
    private List<Price> prices;

    private String subaccountId;

    @Data
    public static class Price {
        private boolean recurring;
        private String productId;
    }
}
