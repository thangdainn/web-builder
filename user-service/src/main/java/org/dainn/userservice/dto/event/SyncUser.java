package org.dainn.userservice.dto.event;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyncUser {
    private UserProducer user;
    private boolean updatePer;
}
