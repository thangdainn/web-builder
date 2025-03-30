package org.dainn.subscriptionservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.subscriptionservice.util.enums.Plan;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto extends AbstractDto {
    @NotNull(message = "Plant is required")
    private Plan plant;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime currentPeriodEndDate;

    private Boolean active;

    @NotNull(message = "Customer ID is required")
    @NotBlank(message = "Customer ID is required")
    private String customerId;

    @NotNull(message = "Agency ID is required")
    @NotBlank(message = "Agency ID is required")
    private String agencyId;
}
