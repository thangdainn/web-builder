package org.dainn.userservice.dto.subaccount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.dainn.userservice.dto.AbstractDto;
import org.dainn.userservice.util.enums.Icon;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SubAccountSODto extends AbstractDto {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Link is required")
    @NotBlank(message = "Link is required")
    private String link;

    @NotNull(message = "Icon is required")
    @NotBlank(message = "Icon is required")
    private Icon icon;

    private String subAccountId;
}
