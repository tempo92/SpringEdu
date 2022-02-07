package org.tempo.springEdu.contoller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tempo.springEdu.dto.UserDto;
import org.tempo.springEdu.entity.AuthRequest;
import org.tempo.springEdu.entity.User;
import org.tempo.springEdu.security.JwtTokenUtil;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/public")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ModelMapper modelMapper = new ModelMapper();

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid AuthRequest request) {
        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(), request.getPassword()
                        )
                );

        User user = (User) authenticate.getPrincipal();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtTokenUtil.generateAccessToken(user)
                )
                .body(modelMapper.map(user, UserDto.class));
    }
}
