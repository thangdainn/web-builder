package org.dainn.subaccountservice.dto.subaccount;

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
public class DeleteSubAccount {
    @NotNull(message = "ID cannot be null")
    @NotBlank(message = "ID cannot be blank")
    private String id;

    @NotNull(message = "User email cannot be null")
    @NotBlank(message = "User email cannot be blank")
    private String email;
}
