package org.tempo.springEdu.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.tempo.springEdu.dto.*;
import org.tempo.springEdu.entity.Project;
import org.tempo.springEdu.exception.ProjectNotFoundException;
import org.tempo.springEdu.service.ProjectService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping("/test")
    String home() {
        return "projectController test";
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody ProjectUpdateDto projectDto) {
        projectService.create(projectDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> findAll() {
        return ResponseEntity.ok(projectService.findAllDto());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProjectDto> findById(@PathVariable(value = "id") String id) {
        var projectDto = projectService.findByIdDto(id);
        return ResponseEntity.ok(projectDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") String id) {
        Project delProject = projectService.findById(id);
        projectService.delete(delProject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(
            @PathVariable(value = "id") String id, @Valid @RequestBody ProjectUpdateDto dto) {
        Project optionalProject = projectService.findById(id);
        projectService.update(optionalProject.getId(), dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler({ProjectNotFoundException.class})
    public ResponseEntity<String> handleProjectNotFoundException(
            ProjectNotFoundException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
