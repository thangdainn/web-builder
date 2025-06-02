package org.dainn.userservice.dto.invitation;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.userservice.dto.AbstractDto;
import org.dainn.userservice.util.enums.InvitationStatus;
import org.dainn.userservice.util.enums.Role;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InvitationDto extends AbstractDto {

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    private String email;

    private InvitationStatus status = InvitationStatus.PENDING;

    private Role role = Role.SUBACCOUNT_USER;

    @NotNull(message = "Agency is required")
    @NotBlank(message = "Agency is required")
    private String agencyId;
}
