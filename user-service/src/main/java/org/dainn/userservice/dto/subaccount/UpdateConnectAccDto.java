package org.dainn.userservice.dto.subaccount;

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
public class UpdateConnectAccDto {
    @NotNull(message = "Account Id is required")
    @NotBlank(message = "Account Id is required")
    private String connectAccountId;
}
