package org.dainn.subaccountservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.subaccountservice.util.enums.Icon;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sub_account_sos")
public class SubAccountSidebarOption extends BaseEntity {
    private String name;
    private String link;

    @Enumerated(EnumType.STRING)
    private Icon icon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_account_id")
    private SubAccount subAccount;
}
