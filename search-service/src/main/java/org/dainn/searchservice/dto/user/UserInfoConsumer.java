package org.dainn.searchservice.dto.user;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoConsumer {
    private String id;
    private String agencyId;
    private List<UserInfoConsumer.Permission> permissions;
    private List<UserInfoConsumer.SubAccount> subAccounts;

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
}
