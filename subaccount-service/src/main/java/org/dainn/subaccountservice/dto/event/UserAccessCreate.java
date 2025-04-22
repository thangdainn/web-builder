package org.dainn.subaccountservice.dto.event;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccessCreate {
    private String userId;
    private List<UserProducer.Permission> permission;
}
