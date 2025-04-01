package org.dainn.pipelineservice.dto.lane;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.pipelineservice.dto.AbstractDto;
import org.dainn.pipelineservice.dto.ticket.TicketDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaneDetailDto extends AbstractDto {
    private String name;

    private Integer order;

    private String pipelineId;

    private List<TicketDto> tickets = new ArrayList<>();
}
