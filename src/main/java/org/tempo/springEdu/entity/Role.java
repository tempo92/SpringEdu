package org.tempo.springEdu.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
    private String authority;

    public void SetAuthority(RoleName roleName) {
        authority = roleName.toString();
    }

    public Role(RoleName roleName) {
        authority = roleName.toString();
    }
}
