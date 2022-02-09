package org.tempo.springEdu.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.tempo.springEdu.entity.FileDescription;

import java.util.List;

public interface FileDescriptionRepository
        extends MongoRepository<FileDescription, String>, FileDescriptionRepositoryExtension {

    List<FileDescription> findByDirectory(String directory);
}
