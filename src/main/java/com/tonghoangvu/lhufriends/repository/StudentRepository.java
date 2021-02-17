package com.tonghoangvu.lhufriends.repository;

import com.tonghoangvu.lhufriends.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {}
