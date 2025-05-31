package org.dainn.subaccountservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {
    private BigDecimal price;
    private String priceId;

    private LocalDate currentPeriodEndDate;

    private Boolean active;

    private String customerId;

    private String agencyId;
    private String subscriptionId;
}
