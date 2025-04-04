package org.dainn.subaccountservice.dto.subaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.subaccountservice.dto.PageRequest;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubAccountReq extends PageRequest {
    private String keyword;
    private String agencyId;
}
