package org.dainn.pipelineservice.dto.lane;

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
public class LaneOrderDto {
    @NotNull(message = "Lane Id is required")
    @NotBlank(message = "Lane Id is required")
    private String laneId;

    @NotNull(message = "Order is required")
    @NotBlank(message = "Order is required")
    private Integer order;
}
