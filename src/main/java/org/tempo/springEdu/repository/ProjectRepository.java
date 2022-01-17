package org.tempo.springEdu.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tempo.springEdu.entity.Project;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
}
