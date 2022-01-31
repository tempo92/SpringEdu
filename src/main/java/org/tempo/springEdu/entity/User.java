package org.tempo.springEdu.entity;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Data
@AllArgsConstructor
public class User implements UserDetails {
    private String username;
    private String password;
    private Set<Role> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
