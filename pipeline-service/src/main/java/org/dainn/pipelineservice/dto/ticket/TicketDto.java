package org.dainn.pipelineservice.dto.ticket;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.pipelineservice.dto.AbstractDto;
import org.dainn.pipelineservice.dto.tag.TagDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto extends AbstractDto {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;

    private Integer order;

    private BigDecimal value;

    private String description;

    private String assignedUserId;
    private String assignedName;

    private String customerId;
    private String customerName;


    @NotNull(message = "Lane is required")
    @NotBlank(message = "Lane is required")
    private String laneId;

    @Valid
    private List<TagDto> tags = new ArrayList<>();
}
