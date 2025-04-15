package org.dainn.paymentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgencyDto {
    private String id;

    private String connectAccountId;

    private String customerId;

    private String name;

    private String companyLogo;

    private String companyEmail;

    private String companyPhone;

    private Boolean whiteLabel = true;

    private String address;

    private String city;

    private String zipCode;

    private String state;

    private String country;

    private Integer goal = 5;
    private SubscriptionResp subscription;
}
