package org.dainn.agencyservice.model;

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
@Table(name = "agencies")
public class Agency extends BaseEntity {
    private String connectAccountId;

    private String customerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String companyLogo;

    @Column(nullable = false)
    private String companyEmail;

    @Column(nullable = false)
    private String companyPhone;

    @Column(nullable = false)
    private Boolean whiteLabel;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private Integer goal;

    @OneToMany(mappedBy = "agency")
    private List<AgencySidebarOption> sidebarOptions = new ArrayList<>();
}
