package org.dainn.userservice.dto.event;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteAgencyEvent {
    private String agencyId;
    private String email;

}
