package org.tempo.springEdu.repository;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.tempo.springEdu.entity.Project;

import java.util.List;

@RequiredArgsConstructor
public class ProjectRepositoryExtensionImpl implements ProjectRepositoryExtension {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Project> findByName(
            @Nullable String namePart,
            @Nullable Pageable pageable,
            @Nullable Sort sort) {

        Query query = new Query();
        if (namePart != null && !namePart.isEmpty()) {
            query.addCriteria(Criteria.where("name").regex(namePart, "i"));
        }
        if (pageable != null) {
            query.with(pageable);
        }
        if (sort != null) {
            query.with(sort);
        }
        return mongoTemplate.find(query, Project.class);
    }

}
