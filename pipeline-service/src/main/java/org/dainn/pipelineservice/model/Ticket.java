package org.dainn.pipelineservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    //    user id
    private String assignedUserId;

    // contact id
    private String customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lane_id")
    private Lane lane;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ticket_tags",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<>();
}
