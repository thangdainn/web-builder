package org.dainn.userservice.dto.invitation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.userservice.dto.PageRequest;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InvitationReq extends PageRequest {
    private String email;
}
