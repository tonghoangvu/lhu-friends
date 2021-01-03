package com.tonghoangvu.lhufriends.repository;

import com.tonghoangvu.lhufriends.entities.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomStudentRepository {
    private final MongoOperations mongoOperations;

    private Criteria regexCriteria(String field, String value) {
        return Criteria.where(field).regex(value, "i");
    }

    public List<Student> findAllWithFilterAndPagination(Student studentFilter, int page, int size) {
        // Build query
        Query query = new Query();
        query.with(Sort.by("studentId").descending());
        query.with(PageRequest.of(page, size));

        // Exact match fields
        if (studentFilter.getGender() != null)
            query.addCriteria(Criteria.where("gender").is(studentFilter.getGender()));

        // Regex match fields
        if (studentFilter.getStudentId() != null)
            query.addCriteria(regexCriteria("studentId", studentFilter.getStudentId()));
        if (studentFilter.getFullName() != null)
            query.addCriteria(regexCriteria("fullName", studentFilter.getFullName()));
        if (studentFilter.getBirthday() != null)
            query.addCriteria(regexCriteria("birthday", studentFilter.getBirthday()));
        if (studentFilter.getPlaceOfBirth() != null)
            query.addCriteria(regexCriteria("placeOfBirth", studentFilter.getPlaceOfBirth()));
        if (studentFilter.getEthnic() != null)
            query.addCriteria(regexCriteria("ethnic", studentFilter.getEthnic()));
        if (studentFilter.getNationality() != null)
            query.addCriteria(regexCriteria("nationality", studentFilter.getNationality()));
        if (studentFilter.getClassId() != null)
            query.addCriteria(regexCriteria("classId", studentFilter.getClassId()));
        if (studentFilter.getFacebook() != null)
            query.addCriteria(regexCriteria("facebook", studentFilter.getFacebook()));
        if (studentFilter.getEmail() != null)
            query.addCriteria(regexCriteria("email", studentFilter.getEmail()));
        if (studentFilter.getPhone() != null)
            query.addCriteria(regexCriteria("phone", studentFilter.getPhone()));

        // Query
        return mongoOperations.find(query, Student.class);
    }
}
