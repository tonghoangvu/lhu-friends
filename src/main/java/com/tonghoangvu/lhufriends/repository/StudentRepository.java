package com.tonghoangvu.lhufriends.repository;

import com.tonghoangvu.lhufriends.entity.StudentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<StudentEntity, String> {}
