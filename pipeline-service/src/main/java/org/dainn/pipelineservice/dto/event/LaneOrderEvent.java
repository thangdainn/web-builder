package org.dainn.pipelineservice.dto.event;

import lombok.*;
import org.dainn.pipelineservice.dto.lane.LaneOrderDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LaneOrderEvent {
    private String pipelineId;
    private List<LaneOrderDto> list;
}
