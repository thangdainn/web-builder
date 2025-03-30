package org.dainn.funnelservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "class_names")
public class ClassName extends BaseEntity {
    private String name;

    private String color;

    private String customData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funnel_id")
    private Funnel funnel;
}
