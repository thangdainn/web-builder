package org.dainn.subaccountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubAccountDto extends AbstractDto{
    private String connectAccountId;
    private String name;
    private String subAccountLogo;
    private String companyEmail;
    private String companyPhone;
    private String address;
    private Integer goal;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String agencyId;
}
