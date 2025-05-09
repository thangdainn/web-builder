package org.dainn.pipelineservice.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.pipelineservice.dto.tag.TagDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTicketDto {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;

    private BigDecimal value;

    private String description;

    private String assignedUserId;

    private String customerId;

    private List<TagDto> tags = new ArrayList<>();
}
