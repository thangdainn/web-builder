package org.dainn.userservice.dto.permission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionDto {
    private String id;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    private String email;

    private String userId;
    private String subAccountId;

    private Boolean access;
}
