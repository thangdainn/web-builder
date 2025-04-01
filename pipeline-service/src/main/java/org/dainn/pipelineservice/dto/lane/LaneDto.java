package org.dainn.pipelineservice.dto.lane;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.pipelineservice.dto.AbstractDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaneDto extends AbstractDto {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Order is required")
    private Integer order;

    @NotNull(message = "Pipeline is required")
    @NotBlank(message = "Pipeline is required")
    private String pipelineId;
}
