package org.tempo.springEdu.contoller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tempo.springEdu.dto.ProjectDto;
import org.tempo.springEdu.entity.Project;
import org.tempo.springEdu.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository repository;

    private final ModelMapper modelMapper = new ModelMapper();

    @RequestMapping("/test")
    String home() {
        return "projectController test";
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody ProjectDto aProjectDto) {
        Project aProject = modelMapper.map(aProjectDto, Project.class);
        repository.save(aProject);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Project>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Project> findById(@PathVariable(value = "id") String id) {
        var project = repository.findById(id);
        if (project.isPresent()) {
            return ResponseEntity.ok(project.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") String id) {
        Optional<Project> delProject = repository.findById(id);
        if (delProject.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            repository.delete(delProject.get());
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(
            @PathVariable(value = "id") String id, @RequestBody ProjectDto projectDto) {
        Optional<Project> optionalProject = repository.findById(id);
        if (optionalProject.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            Project aProject = modelMapper.map(projectDto, Project.class);
            aProject.setId(id);
            repository.save(aProject);
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
