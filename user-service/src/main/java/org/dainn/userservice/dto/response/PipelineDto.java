package org.dainn.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.dainn.userservice.dto.AbstractDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PipelineDto extends AbstractDto {
    private String name;
    private String subAccountId;
}
