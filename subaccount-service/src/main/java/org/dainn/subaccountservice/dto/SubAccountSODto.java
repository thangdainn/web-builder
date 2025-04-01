package org.dainn.subaccountservice.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.subaccountservice.model.SubAccount;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubAccountSODto extends AbstractDto {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Link is required")
    @NotBlank(message = "Link is required")
    private String link;

    @NotNull(message = "Icon is required")
    @NotBlank(message = "Icon is required")
    private String icon;

    private String subAccountId;
}
