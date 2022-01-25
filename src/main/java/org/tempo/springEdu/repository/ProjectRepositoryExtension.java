package org.tempo.springEdu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.tempo.springEdu.entity.Project;

import java.util.List;

public interface ProjectRepositoryExtension {
    List<Project> findByName(String substring, Pageable pageable);
}
