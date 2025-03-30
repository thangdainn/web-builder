package org.dainn.pipelineservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
@Table(name = "pipelines")
public class Pipeline extends BaseEntity {
    private String name;

    private String subAccountId;

    @OneToMany(mappedBy = "pipeline")
    private List<Lane> lanes = new ArrayList<>();
}
