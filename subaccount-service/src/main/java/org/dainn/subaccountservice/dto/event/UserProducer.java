package org.dainn.subaccountservice.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dainn.subaccountservice.dto.subaccount.SubAccountDetailDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProducer {
    private String id;
    private String name;
    private String avatarUrl;
    private String email;
    private String role;
    private Agency agency;
    private List<Permission> permissions;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Agency {
        private String id;
        private String name;
        private String agencyLogo;
        private List<SubAccount> subAccounts;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Permission {
        private String id;
        private SubAccount subAccount;
        private Boolean access;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SubAccount {
        private String id;
        private String name;
        private List<SidebarOption> sidebarOptions;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SidebarOption {
        private String id;
        private String name;
        private String link;
        private String icon;
    }

    public static SubAccount toSubAccount(SubAccountDetailDto dto) {
        return SubAccount.builder()
                .id(dto.getId())
                .name(dto.getName())
                .sidebarOptions(dto.getOptions().stream()
                        .map(option -> SidebarOption.builder()
                                .id(option.getId())
                                .name(option.getName())
                                .link(option.getLink())
                                .icon(option.getIcon().toString())
                                .build())
                        .toList())
                .build();
    }
}