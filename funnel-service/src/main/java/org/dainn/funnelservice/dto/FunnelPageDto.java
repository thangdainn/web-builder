package org.dainn.funnelservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FunnelPageDto extends AbstractDto {
    private String name;

    private String pathName;

    private Integer visits;

    private String content;

    private Integer order;

    private String previewImage;

    private String funnelId;
}
