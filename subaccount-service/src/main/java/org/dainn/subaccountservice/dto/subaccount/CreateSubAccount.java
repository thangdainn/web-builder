package org.dainn.subaccountservice.dto.subaccount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.subaccountservice.dto.AbstractDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSubAccount extends AbstractDto {
    private String connectAccountId;

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Logo is required")
    @NotBlank(message = "Logo is required")
    private String subAccountLogo;

    @NotNull(message = "Company name is required")
    @NotBlank(message = "Company name is required")
    private String companyEmail;

    @NotNull(message = "Company phone is required")
    @NotBlank(message = "Company phone is required")
    private String companyPhone;

    @NotNull(message = "Company email is required")
    @NotBlank(message = "Company email is required")
    private String address;

    private Integer goal = 5;

    private String city;
    private String state;
    private String country;
    private String zipCode;

    @NotNull(message = "Agency id is required")
    @NotBlank(message = "Agency id is required")
    private String agencyId;

    @NotNull(message = "User email is required")
    private String userEmail;
}
