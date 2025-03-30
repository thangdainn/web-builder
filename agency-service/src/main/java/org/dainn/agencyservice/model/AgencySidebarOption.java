package org.dainn.agencyservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.agencyservice.util.enums.Icon;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "agency_sos")
public class AgencySidebarOption extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String link;

    @Enumerated(EnumType.STRING)
    private Icon icon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id")
    private Agency agency;
}
