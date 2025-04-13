package org.dainn.userservice.dto.user;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccessProducer {
    private String userId;
    private String permissionId;
    private Boolean access;
}
