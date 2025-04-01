package org.dainn.subaccountservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionDto {
    private String id;

    private String userId;
    private String email;

    private String subAccountId;

    private Boolean access;
}
