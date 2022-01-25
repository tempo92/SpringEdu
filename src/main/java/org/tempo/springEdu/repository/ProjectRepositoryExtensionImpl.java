package org.tempo.springEdu.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.tempo.springEdu.entity.Project;

import java.util.List;

@RequiredArgsConstructor
public class ProjectRepositoryExtensionImpl implements ProjectRepositoryExtension {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Project> findByName(String substring, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(substring, "i"));
        query.with(pageable);
        return mongoTemplate.find(query, Project.class);
    }
}
