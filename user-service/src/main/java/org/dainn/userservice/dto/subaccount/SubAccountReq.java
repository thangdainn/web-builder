package org.dainn.userservice.dto.subaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.userservice.dto.PageRequest;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubAccountReq extends PageRequest {
    private String keyword;
    private String agencyId;
}
