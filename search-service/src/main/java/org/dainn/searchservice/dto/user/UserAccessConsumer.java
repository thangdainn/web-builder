package org.dainn.searchservice.dto.user;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccessConsumer {
    private String userId;
    private String permissionId;
    private Boolean access;
}
