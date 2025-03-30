package org.dainn.pipelineservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaneDto extends AbstractDto {
    private String name;
    private Integer order;
    private String pipelineId;
}
