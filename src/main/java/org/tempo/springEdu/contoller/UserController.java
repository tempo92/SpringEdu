package org.tempo.springEdu.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.tempo.springEdu.dto.ProjectDto;
import org.tempo.springEdu.dto.ProjectUpdateDto;
import org.tempo.springEdu.dto.UserDto;
import org.tempo.springEdu.dto.UserUpdateDto;
import org.tempo.springEdu.entity.User;
import org.tempo.springEdu.service.ProjectService;
import org.tempo.springEdu.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    String home() {
        return "UserController test";
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAllDto());
    }

    @PostMapping("")
    public ResponseEntity<?> create(
            @Valid @RequestBody UserUpdateDto userUpdateDto,
            @AuthenticationPrincipal User user) {
        userService.create(userUpdateDto, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(value = "id") String id, @AuthenticationPrincipal User user) {
        userService.delete(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(
            @PathVariable(value = "id") String id, @Valid @RequestBody UserUpdateDto dto,
            @AuthenticationPrincipal User user) {
        userService.update(id, dto, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
