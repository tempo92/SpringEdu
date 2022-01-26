package org.tempo.springEdu.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tempo.springEdu.dto.*;
import org.tempo.springEdu.service.ProjectService;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<List<ProjectDto>> findAll(
            @RequestParam(value = "filterProjectName", required = false) String nameFilter
            , @RequestParam(value = "pageNumber", required = false) Integer pageNumber
            , @RequestParam(value = "pageSize", required = false) Integer pageSize
            , @RequestParam(value = "sort", required = false) List<String> sortInfo) {

        return ResponseEntity.ok(
                projectService.findAllDto(nameFilter, pageNumber, pageSize, sortInfo));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProjectDto> findById(@PathVariable(value = "id") String id) {
        var projectDto = projectService.findByIdDto(id);
        return ResponseEntity.ok(projectDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") String id) {
        projectService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(
            @PathVariable(value = "id") String id, @Valid @RequestBody ProjectUpdateDto dto) {
        projectService.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
