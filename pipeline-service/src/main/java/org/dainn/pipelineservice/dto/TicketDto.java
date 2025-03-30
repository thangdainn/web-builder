package org.dainn.pipelineservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto extends AbstractDto {
    private String name;
    private Integer order;
    private BigDecimal value;
    private String description;

    private String assignedUserId;
    private String laneId;
}
