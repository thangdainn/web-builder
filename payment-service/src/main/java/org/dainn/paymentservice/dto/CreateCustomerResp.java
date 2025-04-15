package org.dainn.paymentservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerResp {
    private String customerId;
    private String name;
    private String email;
    private String status;
    private String message;
}
