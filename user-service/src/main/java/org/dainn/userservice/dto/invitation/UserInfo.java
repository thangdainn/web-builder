package org.dainn.userservice.dto.invitation;

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
public class UserInfo {
    @NotNull(message = "ID is required")
    @NotBlank(message = "ID is required")
    private String id;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    private String email;

    private String name;

    private String avatarUrl;
}
