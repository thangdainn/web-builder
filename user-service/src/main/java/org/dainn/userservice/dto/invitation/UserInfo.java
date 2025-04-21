package org.dainn.userservice.dto.invitation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.userservice.dto.AbstractDto;
import org.dainn.userservice.util.enums.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo extends AbstractDto {
    @NotNull(message = "ID is required")
    @NotBlank(message = "ID is required")
    private String id;

    private String name;

    private String avatarUrl;

    private String token;
}
