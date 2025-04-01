package org.dainn.pipelineservice.dto.pipeline;

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
public class PipelineDto extends AbstractDto {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "SubAccount is required")
    @NotBlank(message = "SubAccount is required")
    private String subAccountId;
}
