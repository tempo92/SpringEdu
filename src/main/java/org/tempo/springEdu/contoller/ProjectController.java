package org.tempo.springEdu.contoller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tempo.springEdu.dto.ProjectDto;
import org.tempo.springEdu.entity.Project;
import org.tempo.springEdu.repository.ProjectRepository;
import org.tempo.springEdu.service.ProjectService;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity create(@RequestBody ProjectDto projectDto) {
        projectService.create(projectDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Project>> findAll() {
        return ResponseEntity.ok(projectService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Project> findById(@PathVariable(value = "id") String id) {
        var project = projectService.findById(id);
        if (project.isPresent()) {
            return ResponseEntity.ok(project.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") String id) {
        Optional<Project> delProject = projectService.findById(id);
        if (delProject.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            projectService.delete(delProject.get());
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(
            @PathVariable(value = "id") String id, @RequestBody ProjectDto projectDto) {
        Optional<Project> optionalProject = projectService.findById(id);
        if (optionalProject.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            projectService.update(id, projectDto);
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
