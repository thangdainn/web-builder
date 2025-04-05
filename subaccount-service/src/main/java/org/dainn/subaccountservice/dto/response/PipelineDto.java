package org.dainn.subaccountservice.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.dainn.subaccountservice.dto.AbstractDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PipelineDto extends AbstractDto {
    private String name;
    private String subAccountId;
}
