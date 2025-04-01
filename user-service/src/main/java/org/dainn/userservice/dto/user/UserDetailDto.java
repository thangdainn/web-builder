package org.dainn.userservice.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.userservice.dto.AbstractDto;
import org.dainn.userservice.dto.permission.PermissionDto;
import org.dainn.userservice.util.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto extends AbstractDto {
    private String name;

    private String email;

    private String avatarUrl;

    private Role role;

    private String agencyId;

    private List<PermissionDto> permissions = new ArrayList<>();
}
