package org.tempo.springEdu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.tempo.springEdu.entity.Role;
import org.tempo.springEdu.entity.RoleName;
import org.tempo.springEdu.entity.User;
import org.tempo.springEdu.repository.UserRepository;
import org.tempo.springEdu.service.CustomUserDetailsService;

import java.util.Set;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        if (userRepository.findByUsername("user").isEmpty()){
            var user = new User("user",
                    passwordEncoder().encode("user"),
                    Set.of(new Role(RoleName.USER))
            );
            userRepository.save(user);
        }
        if (userRepository.findByUsername("admin").isEmpty()){
            var user = new User("admin",
                    passwordEncoder().encode("admin"),
                    Set.of(new Role(RoleName.ADMIN))
            );
            userRepository.save(user);
        }

        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/**").authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors().and().csrf().disable();
    }
}
