package org.dainn.userservice.dto.contact;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.dainn.userservice.dto.AbstractDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ContactDto extends AbstractDto {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Email number is required")
    @NotBlank(message = "Email number is required")
    private String email;

    @NotNull(message = "SubAccount number is required")
    @NotBlank(message = "SubAccount number is required")
    private String subAccountId;

    private boolean active;
}
