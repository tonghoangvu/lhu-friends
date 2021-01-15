package com.tonghoangvu.lhufriends.repository;

import com.tonghoangvu.lhufriends.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findFirstByUsername(String username);
}
