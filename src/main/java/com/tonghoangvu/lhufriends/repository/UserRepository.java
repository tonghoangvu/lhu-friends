package com.tonghoangvu.lhufriends.repository;

import com.tonghoangvu.lhufriends.entity.User;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    @Nullable User findFirstByUsername(String username);
}
