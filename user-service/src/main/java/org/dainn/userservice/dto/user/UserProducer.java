package org.dainn.userservice.dto.user;

import lombok.*;
import org.dainn.userservice.model.User;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProducer {
    private String id;
    private String name;
    private String avatarUrl;
    private String email;
    private String role;
    private String agencyId;
    private List<UserProducer.Permission> permissions;
    private List<UserProducer.SubAccount> subAccounts;

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

    public static UserProducer toProducer(User user, List<SubAccount> subAccount) {
        UserProducer dto = new UserProducer();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());
        dto.setAgencyId(user.getAgencyId());
        dto.setPermissions(user.getPermissions().stream()
                .map(permission -> {
                    UserProducer.Permission p = new UserProducer.Permission();
                    p.setId(permission.getId());
                    p.setSubAccountId(permission.getSubAccountId());
                    p.setAccess(permission.getAccess());
                    return p;
                }).toList());
        dto.setSubAccounts(subAccount);
        return dto;
    }
}
