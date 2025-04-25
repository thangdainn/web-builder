package org.dainn.agencyservice.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String name;
    private String email;
    private String avatarUrl;
    private String agencyId;
}
