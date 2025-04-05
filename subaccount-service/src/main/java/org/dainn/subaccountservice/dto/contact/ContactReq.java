package org.dainn.subaccountservice.dto.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.subaccountservice.dto.PageRequest;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactReq extends PageRequest {
    private String keyword;
}
