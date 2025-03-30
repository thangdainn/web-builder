package org.dainn.funnelservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FunnelDto extends AbstractDto {
    private String name;

    private String description;

    private Boolean published;

    private String subDomainName;

    private String favicon;

    private String liveProducts;

    private String subAccountId;
}
