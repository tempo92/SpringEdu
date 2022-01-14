package org.tempo.springEdu.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tempo.springEdu.entity.project;

@Repository
public interface projectRepository extends MongoRepository<project, String> {
}
