package org.dainn.agencyservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomer {
    private String email;
    private String name;
    private String phone;
    private String line1;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String agencyId;
}
