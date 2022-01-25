package org.tempo.springEdu.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.tempo.springEdu.entity.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<Project> findByAreaRegexIgnoreCase(String regex);
}
