package org.dainn.searchservice.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.searchservice.dto.PageRequest;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserReq extends PageRequest {
    private String email;
    private String agencyId;
}
