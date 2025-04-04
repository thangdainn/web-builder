package org.dainn.agencyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgencyDetailDto extends AbstractDto{
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

    private List<AgencySODto> options = new ArrayList<>();
    private List<SubscriptionResp> subscriptions;
}
