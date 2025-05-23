package org.dainn.funnelservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "funnels")
public class Funnel extends BaseEntity {
    private String name;

    private String description;

    private Boolean published;

    private String subDomainName;

    private String favicon;

    private String liveProducts;

    private String subAccountId;

}
