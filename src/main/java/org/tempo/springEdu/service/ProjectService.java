package org.tempo.springEdu.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempo.springEdu.dto.*;
import org.tempo.springEdu.entity.Project;
import org.tempo.springEdu.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository repository;

    private final ModelMapper modelMapper = new ModelMapper();

    public void update(String id, ProjectUpdateDto dto) {
        Project project = dtoToEntity(dto);
        project.setId(id);
        repository.save(project);
    }

    public void delete(Project project) {
        repository.delete(project);
    }

    public Optional<Project> findById(String id) {
        return repository.findById(id);
    }

    public Optional<ProjectDto> findByIdDto(String id) {
        var project = repository.findById(id);
        return project.map(this::entityToDto);
    }

    public List<Project> findAll() {
        return repository.findAll();
    }

    public List<ProjectDto> findAllDto() {
        return repository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public void create(ProjectUpdateDto projectDto) {
        repository.save(dtoToEntity(projectDto));
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
}
