package org.dainn.funnelservice.dto.funnel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.funnelservice.dto.AbstractDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FunnelDto extends AbstractDto {
    private String name;

    private String description;

    private Boolean published = false;

    private String subDomainName;

    private String favicon;

    private String liveProducts;

    private String subAccountId;
}
