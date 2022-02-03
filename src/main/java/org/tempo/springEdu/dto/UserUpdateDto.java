package org.tempo.springEdu.dto;

import lombok.*;

import org.tempo.springEdu.entity.Role;

import java.util.Set;

@Data
public class UserUpdateDto {
    private String username;
    private String password;
    private Set<Role> authorities;
}
