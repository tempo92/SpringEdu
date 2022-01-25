package org.tempo.springEdu.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.tempo.springEdu.entity.Project;

import java.util.List;

public interface ProjectRepositoryExtension {

    List<Project> findByName(String namePart, Pageable pageable, Sort sort);
}
