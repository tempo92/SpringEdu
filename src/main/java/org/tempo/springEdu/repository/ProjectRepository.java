package org.tempo.springEdu.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tempo.springEdu.entity.Project;

import java.util.List;

@Repository
public interface ProjectRepository
        extends MongoRepository<Project, String>, ProjectRepositoryExtension {

    List<Project> findByNameContainsIgnoreCase(String substring);
    List<Project> findByNameContainsIgnoreCase(String substring, Pageable pageable);

}
