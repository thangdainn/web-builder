package org.dainn.agencyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.agencyservice.util.enums.Plan;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionResp {
    private Plan plan;

    private BigDecimal price;
    private String priceId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime currentPeriodEndDate;

    private Boolean active;

    private String customerId;

    private String agencyId;
    private String subscriptionId;
}
