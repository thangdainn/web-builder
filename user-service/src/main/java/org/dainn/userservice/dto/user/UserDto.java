package org.dainn.userservice.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.dainn.userservice.dto.AbstractDto;
import org.dainn.userservice.util.enums.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends AbstractDto {
    private String name;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    private String email;

    private String avatarUrl;

    private Role role = Role.SUBACCOUNT_USER;

    private String agencyId;
}
