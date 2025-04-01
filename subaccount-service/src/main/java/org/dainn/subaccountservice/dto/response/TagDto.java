package org.dainn.subaccountservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.subaccountservice.dto.AbstractDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagDto extends AbstractDto {
    private String name;
    private String color;
    private String subAccountId;
}
