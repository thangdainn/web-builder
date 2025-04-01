package org.dainn.pipelineservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
@Table(name = "tags")
public class Tag extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    private String subAccountId;

    @ManyToMany(mappedBy = "tags")
    private List<Ticket> tickets = new ArrayList<>();
}
