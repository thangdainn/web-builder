package org.dainn.notificationservice.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationMessage {
    private String notification;

    private String agencyId;

    private String subAccountId;

    private String userId;
}
