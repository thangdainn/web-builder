package org.dainn.funnelservice.dto.event;

import lombok.*;
import org.dainn.funnelservice.dto.FPOrderDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FPOrderEvent {
    private String funnelId;
    private List<FPOrderDto> list;
}
