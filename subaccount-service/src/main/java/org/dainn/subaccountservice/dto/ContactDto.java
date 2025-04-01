package org.dainn.subaccountservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
}
