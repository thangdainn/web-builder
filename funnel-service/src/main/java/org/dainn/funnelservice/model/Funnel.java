package org.dainn.funnelservice.model;

import jakarta.persistence.Column;
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
@Table(name = "funnels")
public class Funnel extends BaseEntity {
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean published;

    private String subDomainName;

    private String favicon;

    private String liveProducts;

    private String subAccountId;

    @OneToMany(mappedBy = "funnel")
    private List<FunnelPage> funnelPages = new ArrayList<>();

    @OneToMany(mappedBy = "funnel")
    private List<ClassName> classNames = new ArrayList<>();
}
