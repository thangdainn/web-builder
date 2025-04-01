package org.dainn.mediaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediaReq extends PageRequest {
    private String keyword;
    private String userId;
    private String subAccountId;
    private String agencyId;
}
