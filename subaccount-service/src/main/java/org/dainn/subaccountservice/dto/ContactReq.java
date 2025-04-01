package org.dainn.subaccountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactReq extends PageRequest{
    private String keyword;
    private String subAccountId;
}
