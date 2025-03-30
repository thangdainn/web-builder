package org.dainn.agencyservice.dto;

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
public class AgencyDto extends AbstractDto{
    private String connectAccountId;

    private String customerId;

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Company name is required")
    @NotBlank(message = "Company name is required")
    private String companyLogo;

    @NotNull(message = "Company name is required")
    @NotBlank(message = "Company name is required")
    private String companyEmail;

    @NotNull(message = "Company name is required")
    @NotBlank(message = "Company name is required")
    private String companyPhone;

    private Boolean whiteLabel = true;

    @NotNull(message = "Address is required")
    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "City is required")
    @NotBlank(message = "City is required")
    private String city;

    @NotNull(message = "Zip code is required")
    @NotBlank(message = "Zip code is required")
    private String zipCode;

    @NotNull(message = "State is required")
    @NotBlank(message = "State is required")
    private String state;

    @NotNull(message = "Country is required")
    @NotBlank(message = "Country is required")
    private String country;

    private Integer goal = 5;
}
