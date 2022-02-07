package org.tempo.springEdu.contoller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.tempo.springEdu.SpringEduApplication;
import org.tempo.springEdu.dto.*;
import org.tempo.springEdu.entity.User;
import org.tempo.springEdu.service.ProjectService;
import org.tempo.springEdu.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping("/test")
    String home(@AuthenticationPrincipal User user) {
        final Logger logger = LogManager.getLogger(this.getClass());
        logger.info("test; user: " + (user != null ? user.getUsername() : "null"));
        return "projectController test";
    }

    @PostMapping("")
    public ResponseEntity<?> create(
            @Valid @RequestBody ProjectUpdateDto projectDto,
            @AuthenticationPrincipal User user) {
        projectService.create(projectDto, user);
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
    @PreAuthorize("hasPermission(#id, 'projectCheckOwner')")
    public ResponseEntity<?> delete(
            @PathVariable(value = "id") String id, @AuthenticationPrincipal User user) {
        projectService.delete(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasPermission(#id, 'projectCheckOwner')")
    public ResponseEntity<?> update(
            @PathVariable(value = "id") String id, @Valid @RequestBody ProjectUpdateDto dto,
            @AuthenticationPrincipal User user) {
        projectService.update(id, dto, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
