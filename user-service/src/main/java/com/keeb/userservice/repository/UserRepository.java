package com.keeb.userservice.repository;

import com.keeb.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{'emailId': ?0}")
    Optional<User> findByEmailId(String emailId);

    @Query(value = "{'emailId' : ?0}", delete = true)
    void deleteByEmailId(String emailId);

}
