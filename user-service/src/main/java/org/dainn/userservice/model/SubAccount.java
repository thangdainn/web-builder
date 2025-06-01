package org.dainn.userservice.model;

import jakarta.persistence.CascadeType;
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
@Table(name = "sub_accounts")
public class SubAccount extends BaseEntity {
    private String connectAccountId;
    private String name;
    private String subAccountLogo;
    private String companyEmail;
    private String companyPhone;
    private String address;
    private Integer goal;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    // reference keys
    private String agencyId;

    @OneToMany(mappedBy = "subAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Permission> permissions = new ArrayList<>();

    @OneToMany(mappedBy = "subAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "subAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubAccountSidebarOption> sidebarOptions = new ArrayList<>();
}
