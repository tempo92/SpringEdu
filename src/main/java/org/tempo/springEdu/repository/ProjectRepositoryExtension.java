package org.tempo.springEdu.repository;

import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.tempo.springEdu.entity.Project;

import java.util.List;

public interface ProjectRepositoryExtension {

    List<Project> findByName(
            @Nullable String namePart, @Nullable Pageable pageable, @Nullable Sort sort);
}
