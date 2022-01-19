package org.tempo.springEdu.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempo.springEdu.dto.ProjectDto;
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

    public void update(String id, ProjectDto projectDto) {
        Project project = modelMapper.map(projectDto, Project.class);
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
        return project.map(dto->modelMapper.map(project, ProjectDto.class));
    }

    public List<Project> findAll() {
        return repository.findAll();
    }

    public List<ProjectDto> findAllDto() {
        return repository.findAll().stream()
                .map(project -> modelMapper.map(project, ProjectDto.class))
                .collect(Collectors.toList());
    }

    public void create(ProjectDto aProjectDto) {
        Project aProject = modelMapper.map(aProjectDto, Project.class);
        repository.save(aProject);
    }

}
