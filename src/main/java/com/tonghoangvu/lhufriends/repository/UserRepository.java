package com.tonghoangvu.lhufriends.repository;

import com.tonghoangvu.lhufriends.entity.UserEntity;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    @Nullable UserEntity findFirstByUsername(String username);
}
