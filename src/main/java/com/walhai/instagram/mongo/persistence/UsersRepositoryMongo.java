package com.walhai.instagram.mongo.persistence;

import com.walhai.instagram.mongo.model.Users;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories
public interface UsersRepositoryMongo extends MongoRepository<Users, String> {

    List<Users> findAll();

    @Query("{ 'email': { $regex: ?0, $options: 'i' } }")
    List<Users> findByEmailRegex(String emailRegex);

    @Query(value = "{ 'email': { $regex: ?0, $options: 'i' } }", fields = "{ 'email': 1, 'created_at': 1 }")
    List<Users> findByEmailRegexWithProjection(String emailRegex);

    @Query(value = "{ 'email': { $regex: ?0, $options: 'i' } }", fields = "{ 'email': 1, 'created_at': 1 }")
    List<Users> findByEmailRegexWithProjectionAndSorting(String emailRegex, Sort sort);
}