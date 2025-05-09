package org.dainn.pipelineservice.dto.pipeline;

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
public class UpdatePipelineDto {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;
}
