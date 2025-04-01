package org.dainn.userservice.dto.permission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
    private String id;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    private String email;

    private String userId;

    @NotNull(message = "SubAccount is required")
    @NotBlank(message = "SubAccount is required")
    private String subAccountId;

    private Boolean access;
}
