package org.dainn.userservice.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.userservice.dto.PageRequest;
import org.dainn.userservice.util.enums.Role;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserReq extends PageRequest {
    private String email;
    private String agencyId;
    private Role role;
}
