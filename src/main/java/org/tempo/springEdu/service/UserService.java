package org.tempo.springEdu.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tempo.springEdu.dto.UserDto;
import org.tempo.springEdu.dto.UserUpdateDto;
import org.tempo.springEdu.entity.Role;
import org.tempo.springEdu.entity.RoleName;
import org.tempo.springEdu.entity.User;
import org.tempo.springEdu.exception.ObjectNotFoundException;
import org.tempo.springEdu.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void postConstruct() {
        createDefaultUserIfNeed("user", "user", RoleName.USER);
        createDefaultUserIfNeed("admin", "admin", RoleName.ADMIN);
    }

    public List<UserDto> findAllDto() {
        return entityListToDtoList(
                userRepository.findAll());
    }

    public void create(UserUpdateDto userUpdateDto) {
        var newUser = dtoToEntity(userUpdateDto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
    }

    @Transactional
    public void delete(String id) {
        User delUser = findById(id);
        projectService.deleteByOwnerId(delUser.getId());
        userRepository.delete(delUser);
    }

    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("User with id=%s not found", id)));
    }

    public void update(String id, UserUpdateDto dto) {
        User oldUser = findById(id);
        User newUser = dtoToEntity(dto);
        newUser.setId(oldUser.getId());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
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

    private List<UserDto> entityListToDtoList(List<User> users) {
        return users.stream()
                .map(this::entityToDto).collect(Collectors.toList());
    }

    private UserDto entityToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User dtoToEntity(UserUpdateDto dto) {
        return modelMapper.map(dto, User.class);
    }
}
