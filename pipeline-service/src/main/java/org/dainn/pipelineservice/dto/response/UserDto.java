package org.dainn.pipelineservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.pipelineservice.dto.AbstractDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends AbstractDto {
    private String name;
    private String email;
    private String avatarUrl;
}