package org.tempo.springEdu.contoller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tempo.springEdu.dto.projectDto;
import org.tempo.springEdu.entity.project;
import org.tempo.springEdu.repository.projectRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class projectController {

    @Autowired
    private projectRepository repository;

    private final ModelMapper modelMapper = new ModelMapper();

    @RequestMapping("/test")
    String home() {
        return "projectController test";
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody projectDto aProjectDto) {
        project aProject = modelMapper.map(aProjectDto, project.class);
        repository.save(aProject);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<project>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<project> findById(@PathVariable(value = "id") String id) {
        if (repository.findById(id).isEmpty()) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") String id) {
        Optional<project> delProject = repository.findById(id);
        if (delProject.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            repository.delete(delProject.get());
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(
            @PathVariable(value = "id") String id, @RequestBody projectDto projectDto) {
        Optional<project> optionalProject = repository.findById(id);
        if (optionalProject.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            project aProject = modelMapper.map(projectDto, project.class);
            aProject.setId(id);
            repository.save(aProject);
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
