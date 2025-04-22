package org.dainn.userservice.dto.event;

import lombok.*;
import org.dainn.userservice.model.User;

import java.util.ArrayList;
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
    private Agency agency;
    private List<Permission> permissions = new ArrayList<>();

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Agency {
        private String id;
        private String name;
        private String companyLogo;
        private List<SubAccount> subAccounts = new ArrayList<>();
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Permission {
        private String id;
        private SubAccount subAccount;
        private Boolean access;
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SubAccount {
        private String id;
        private String name;
        private List<SidebarOption> options = new ArrayList<>();
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SidebarOption {
        private String id;
        private String name;
        private String link;
        private String icon;
    }

    public static UserProducer toProducerPer(User user, List<Permission> permissions) {
        UserProducer dto = new UserProducer();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());
        dto.setAgency(new Agency());
        dto.setPermissions(permissions);
        return dto;
    }

    public static UserProducer toProducerAgency(User user, Agency agency) {
        UserProducer dto = new UserProducer();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());
        dto.setAgency(agency);
        dto.setPermissions(new ArrayList<>());
        return dto;
    }
}
