package com.walhai.instagram.mongo.persistence;

import com.walhai.instagram.mongo.model.Follows;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories
public interface FollowsRepositoryMongo extends MongoRepository<Follows, String> {

}
