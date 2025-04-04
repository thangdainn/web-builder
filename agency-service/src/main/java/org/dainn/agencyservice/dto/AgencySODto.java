package org.dainn.agencyservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.dainn.agencyservice.util.enums.Icon;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AgencySODto extends AbstractDto{
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Link is required")
    @NotBlank(message = "Link is required")
    private String link;

    private Icon icon;

    @NotNull(message = "Agency Id is required")
    @NotBlank(message = "Agency Id is required")
    private String agencyId;
}
