package org.dainn.pipelineservice.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.pipelineservice.dto.AbstractDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagDto extends AbstractDto {
    private String name;

    private String color;

    private String subAccountId;
}
