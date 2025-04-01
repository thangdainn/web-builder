package org.dainn.funnelservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "class_names")
public class ClassName extends BaseEntity {
    private String name;

    private String color;

    private String customData;

    @Column("funnel_id")
    private String funnelId;
}
