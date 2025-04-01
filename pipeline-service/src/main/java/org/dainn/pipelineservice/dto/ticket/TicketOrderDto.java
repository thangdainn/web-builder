package org.dainn.pipelineservice.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketOrderDto {
    private String ticketId;
    private String laneId;
    private Integer order;
}
