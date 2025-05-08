package org.dainn.pipelineservice.dto.event;

import lombok.*;
import org.dainn.pipelineservice.dto.ticket.TicketOrderList;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketOrderEvent {
    private List<TicketOrderList> list;
}
