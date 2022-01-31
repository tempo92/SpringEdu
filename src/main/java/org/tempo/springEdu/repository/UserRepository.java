package org.tempo.springEdu.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.tempo.springEdu.entity.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
