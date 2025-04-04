package org.dainn.pipelineservice.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketOrderList {
    @NotNull(message = "Lane Id is required")
    @NotBlank(message = "Lane Id is required")
    private String laneId;

    private List<TicketOrderDto> ticketOrders = new ArrayList<>();
}
