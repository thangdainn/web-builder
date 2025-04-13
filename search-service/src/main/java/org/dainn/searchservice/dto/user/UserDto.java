package org.dainn.searchservice.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.searchservice.dto.AbstractDto;

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


    private String agencyId;
}
