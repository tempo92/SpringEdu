package org.tempo.springEdu.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempo.springEdu.dto.ProjectDto;
import org.tempo.springEdu.entity.Project;
import org.tempo.springEdu.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

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
    public List<Project> findAll() {
        return repository.findAll();
    }

    public void create(ProjectDto aProjectDto) {
        Project aProject = modelMapper.map(aProjectDto, Project.class);
        repository.save(aProject);
    }
}
