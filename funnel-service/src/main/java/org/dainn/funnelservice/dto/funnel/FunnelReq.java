package org.dainn.funnelservice.dto.funnel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.funnelservice.dto.PageRequest;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FunnelReq extends PageRequest {
    private String keyword;
    private String subAccountId;
}
