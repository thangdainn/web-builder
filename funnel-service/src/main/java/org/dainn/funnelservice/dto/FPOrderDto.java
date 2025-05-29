package org.dainn.funnelservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FPOrderDto {
    @NotNull(message = "Funnel page Id is required")
    @NotBlank(message = "Funnel page Id is required")
    private String funnelPageId;

    @NotNull(message = "Order is required")
    @NotBlank(message = "Order is required")
    private Integer order;
}
