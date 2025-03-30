package org.dainn.agencyservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.agencyservice.util.enums.Icon;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
