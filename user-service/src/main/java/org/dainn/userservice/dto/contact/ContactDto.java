package org.dainn.userservice.dto.contact;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.userservice.dto.AbstractDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto extends AbstractDto {

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "SubAccount is required")
    @NotBlank(message = "SubAccount is required")
    private String subAccountId;
}
