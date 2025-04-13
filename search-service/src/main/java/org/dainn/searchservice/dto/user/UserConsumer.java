package org.dainn.searchservice.dto.user;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserConsumer {
    private String id;
    private String name;
    private String avatarUrl;
    private String email;
    private String role;
    private String agencyId;
    private List<UserConsumer.Permission> permissions;
    private List<UserConsumer.SubAccount> subAccounts;

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
