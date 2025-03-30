package org.dainn.pipelineservice.model;

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
@Table(name = "lanes")
public class Lane extends BaseEntity {
    private String name;

    @Column(name = "lane_order")
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pipeline_id")
    private Pipeline pipeline;

    @OneToMany(mappedBy = "lane")
    private List<Ticket> tickets = new ArrayList<>();
}
