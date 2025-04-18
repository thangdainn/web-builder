package org.dainn.agencyservice.dto.response;

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
public class UserDto {
    private String id;
    private String name;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    private String email;

    private String avatarUrl;

    private String agencyId;
}
