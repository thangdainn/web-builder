package org.dainn.funnelservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "funnel_pages")
public class FunnelPage extends BaseEntity {
    private String name;

    private String pathName;

    private Integer visits;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "funnel_page_order")
    private Integer order;

    private String previewImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funnel_id")
    private Funnel funnel;
}
