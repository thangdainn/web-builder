package org.dainn.funnelservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassNameDto extends AbstractDto {
    private String name;

    private String color;

    private String customData;

    private String funnelId;
}
