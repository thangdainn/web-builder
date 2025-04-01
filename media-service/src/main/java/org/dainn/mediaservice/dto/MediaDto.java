package org.dainn.mediaservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaDto extends AbstractDto {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;

    private String type;

    @NotNull(message = "Link is required")
    @NotBlank(message = "Link is required")
    private String link;

    @NotNull(message = "SubAccount is required")
    @NotBlank(message = "SubAccount is required")
    private String subAccountId;
}
