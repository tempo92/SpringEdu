package org.tempo.springEdu.dto;

import lombok.*;

import org.tempo.springEdu.entity.Role;

import java.util.Set;

@Data
public class UserDto {

    private String id;
    private String username;
    private Set<Role> authorities;
}
