package org.tempo.springEdu.service;

import org.jetbrains.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.tempo.springEdu.dto.*;
import org.tempo.springEdu.entity.Project;
import org.tempo.springEdu.entity.User;
import org.tempo.springEdu.exception.ArgumentException;
import org.tempo.springEdu.exception.ObjectNotFoundException;
import org.tempo.springEdu.repository.ProjectRepository;
import org.tempo.springEdu.repository.SortHelper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository repository;

    private final ModelMapper modelMapper = new ModelMapper();

    public void update(String id, ProjectUpdateDto dto) {
        Project oldProject = findById(id);
        if (getUserId().equals(oldProject.getOwnerId())) {
            Project project = dtoToEntity(dto);
            project.setId(oldProject.getId());
            project.setOwnerId(getUserId());
            repository.save(project);
        }
        else {
            throw new AccessDeniedException("Owner only");
        }
    }

    public void delete(String id) {
        Project delProject = findById(id);
        if (getUserId().equals(delProject.getOwnerId())) {
            repository.delete(delProject);
        } else {
            throw new AccessDeniedException("Owner only");
        }
    }

    public Project findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Project with id=%s not found", id)));
    }

    public ProjectDto findByIdDto(String id) {
        return entityToDto(findById(id));
    }

    public List<Project> findAll() {
        return repository.findAll();
    }

    public List<ProjectDto> findAllDto() {
        return entityListToDtoList(repository.findAll());
    }

    public List<ProjectDto> findAllDto(
            @Nullable String namePart, @Nullable Integer pageNumber,
            @Nullable Integer pageSize, @Nullable List<String> sortList) {

        if (pageNumber == null ^ pageSize == null) {
            throw new ArgumentException("Parameters 'pageNumber' and 'pageSize' should be set both");
        }

        Pageable pageable = null;
        if (pageNumber != null && pageSize != null) {
            pageable = PageRequest.of(pageNumber, pageSize);
        }

        return entityListToDtoList(
                repository.findByName(namePart, pageable, SortHelper.createSort(sortList)));
    }

    public void create(ProjectUpdateDto projectDto) {
        var project = dtoToEntity(projectDto);
        project.setOwnerId(getUserId());
        repository.save(project);
    }

    private List<ProjectDto> entityListToDtoList(Page<Project> projects) {
        return projects.stream()
                .map(this::entityToDto).collect(Collectors.toList());
    }

    private List<ProjectDto> entityListToDtoList(List<Project> projects) {
        return projects.stream()
                .map(this::entityToDto).collect(Collectors.toList());
    }

    private ProjectDto entityToDto(Project project) {
        return modelMapper.map(project, ProjectDto.class);
    }

    private Project dtoToEntity(ProjectDto dto) {
        return modelMapper.map(dto, Project.class);
    }

    private Project dtoToEntity(ProjectUpdateDto dto) {
        return modelMapper.map(dto, Project.class);
    }

    private String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((User) authentication.getPrincipal()).getId();
    }
}
