package org.tempo.springEdu.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tempo.springEdu.entity.Project;

import java.util.List;

@Repository
public interface ProjectRepository
        extends MongoRepository<Project, String>, ProjectRepositoryExtension {

    List<Project> findByNameContainsIgnoreCase(String substring);
    List<Project> findByNameContainsIgnoreCase(String substring, Pageable pageable);

    default List<Project> findAll(String namePart,
            Integer pageNumber, Integer pageSize) {

        if (namePart != null && !namePart.isEmpty()) {
            if (pageNumber != null && pageSize != null ) {
                return findByNameContainsIgnoreCase(namePart,
                    PageRequest.of(pageNumber, pageSize));
            }
            else {
                return findByNameContainsIgnoreCase(namePart);
            }
        } else {
            if (pageNumber != null && pageSize != null) {
                return findAll(PageRequest.of(pageNumber, pageSize)).getContent();
            }
            else {
                return findAll();
            }
        }
    }

}
