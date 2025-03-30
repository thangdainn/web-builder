package org.dainn.subscriptionservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addons")
public class AddOns extends BaseEntity {
    private String name;
    private BigDecimal price;
    private String priceId;
    private Boolean active;
    private String agencyId;
}
