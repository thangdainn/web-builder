package org.dainn.subaccountservice.dto.event;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteAgencyConsumer {
    private String agencyId;
    private String email;

}
