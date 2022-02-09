package org.tempo.springEdu.repository;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.tempo.springEdu.entity.FileDescription;
import org.tempo.springEdu.entity.Project;

import java.util.List;

@RequiredArgsConstructor
public class FileDescriptionRepositoryExtensionImpl
        implements FileDescriptionRepositoryExtension {
    private final MongoTemplate mongoTemplate;

    @Override
    public List<FileDescription> find(
            String filenamePart, String dir) {

        Query query = new Query();
        if (filenamePart != null && !filenamePart.isEmpty()) {
            query.addCriteria(Criteria.where("filename").regex(filenamePart, "i"));
        }
        if (filenamePart != null && !filenamePart.isEmpty()) {
            query.addCriteria(Criteria.where("directory").is(dir));
        }

        return mongoTemplate.find(query, FileDescription.class);
    }
}
