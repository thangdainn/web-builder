package org.dainn.subaccountservice.dto;

import lombok.*;
import org.dainn.subaccountservice.dto.response.PermissionDto;
import org.dainn.subaccountservice.model.SubAccount;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProducer {
    private String id;
    private String agencyId;
    private List<Permission> permissions;
    private List<SubAccount> subAccounts;

    @Getter @Setter
    public static class Permission {
        private String id;
        private String subAccountId;
        private Boolean access;
    }

    @Getter @Setter
    public static class SubAccount {
        private String id;
        private String agencyId;
    }

    public static UserProducer toProducer(String userId, String agencyId, List<PermissionDto> permissions, List<org.dainn.subaccountservice.model.SubAccount> subAccounts) {
        UserProducer dto = new UserProducer();
        dto.setId(userId);
        dto.setAgencyId(agencyId);
        dto.setPermissions(permissions.stream()
                .map(permission -> {
                    Permission p = new Permission();
                    p.setId(permission.getId());
                    p.setSubAccountId(permission.getSubAccountId());
                    p.setAccess(permission.getAccess());
                    return p;
                }).toList());
        dto.setSubAccounts(subAccounts.stream()
                .map(subAccount -> {
                    SubAccount sa = new SubAccount();
                    sa.setId(subAccount.getId());
                    sa.setAgencyId(subAccount.getAgencyId());
                    return sa;
                }).toList());
        return dto;
    }
}
