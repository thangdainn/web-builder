package org.dainn.subaccountservice.dto.response;

import lombok.*;
import org.dainn.subaccountservice.dto.AbstractDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PipelineDto extends AbstractDto {
    private String name;
    private String subAccountId;
}
