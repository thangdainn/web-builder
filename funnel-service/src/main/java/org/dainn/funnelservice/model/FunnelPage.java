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
@Table(name = "funnel_pages")
public class FunnelPage extends BaseEntity {
    private String name;

    private String pathName;

    private Integer visits;

    private String content;

    @Column("funnel_page_order")
    private Integer order;

    private String previewImage;

    @Column("funnel_id")
    private String funnelId;
}
