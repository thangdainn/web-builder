package org.dainn.pipelineservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PipelineDto extends AbstractDto {
    private String name;

    private String subAccountId;
}
