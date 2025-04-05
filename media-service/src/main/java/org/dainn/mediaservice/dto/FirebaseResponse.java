package org.dainn.mediaservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FirebaseResponse {
    private String url;
    private boolean success = false;
}
