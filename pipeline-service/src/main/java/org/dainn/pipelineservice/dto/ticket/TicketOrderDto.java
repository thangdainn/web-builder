package org.dainn.pipelineservice.dto.ticket;

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
public class TicketOrderDto {

    @NotNull(message = "Ticket Id is required")
    @NotBlank(message = "Ticket Id is required")
    private String ticketId;

    @NotNull(message = "Order is required")
    @NotBlank(message = "Order is required")
    private Integer order;
}
