package org.tempo.springEdu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.tempo.springEdu.entity.Role;
import org.tempo.springEdu.entity.RoleName;
import org.tempo.springEdu.entity.User;
import org.tempo.springEdu.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void postConstruct() {
        createDefaultUserIfNeed("user", "user", RoleName.ROLE_USER);
        createDefaultUserIfNeed("admin", "admin", RoleName.ROLE_ADMIN);
    }

    private void createDefaultUserIfNeed(String username, String password, RoleName roleName){
        if (userRepository.findByUsername(username).isEmpty()){
            var user = new User(null,
                    username,
                    passwordEncoder.encode(password),
                    Set.of(new Role(roleName))
            );
            userRepository.save(user);
        }
    }

}
