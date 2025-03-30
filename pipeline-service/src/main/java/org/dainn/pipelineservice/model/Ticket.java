package org.dainn.pipelineservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
public class Ticket extends BaseEntity {
    private String name;

    @Column(name = "ticket_order")
    private Integer order;
    private BigDecimal value;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String assignedUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lane_id")
    private Lane lane;
}
