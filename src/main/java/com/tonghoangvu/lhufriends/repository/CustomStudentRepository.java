package com.tonghoangvu.lhufriends.repository;

import com.mongodb.bulk.BulkWriteResult;
import com.tonghoangvu.lhufriends.dto.StudentDto;
import com.tonghoangvu.lhufriends.entity.Student;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomStudentRepository {
    private final @NotNull MongoOperations mongoOperations;

    private @NotNull Criteria regexCriteria(@NotNull String field, @NotNull String value) {
        return Criteria.where(field).regex(value, "i");
    }

    public int upsertStudentList(@NotNull List<StudentDto> studentDtoList) {
        // Create a list of bulk operations
        BulkOperations bulkOps = mongoOperations.bulkOps(
                BulkOperations.BulkMode.UNORDERED, Student.class);

        for (StudentDto studentDto: studentDtoList) {
            // Update not null DTO fields
            Update update = new Update()
                    .currentDate("updatedAt")
                    .setOnInsert("createdAt", new Date());
            if (studentDto.getStudentId() != null)
                update.set("studentId", studentDto.getStudentId());
            if (studentDto.getFullName() != null)
                update.set("fullName", studentDto.getFullName());
            if (studentDto.getBirthday() != null)
                update.set("birthday", studentDto.getBirthday());
            if (studentDto.getGender() != null)
                update.set("gender", studentDto.getGender());
            if (studentDto.getPlaceOfBirth() != null)
                update.set("placeOfBirth", studentDto.getPlaceOfBirth());
            if (studentDto.getEthnic() != null)
                update.set("ethnic", studentDto.getEthnic());
            if (studentDto.getNationality() != null)
                update.set("nationality", studentDto.getNationality());
            if (studentDto.getClassId() != null)
                update.set("classId", studentDto.getClassId());
            if (studentDto.getImage() != null)
                update.set("image", studentDto.getImage());
            if (studentDto.getAvatar() != null)
                update.set("avatar", studentDto.getAvatar());
            if (studentDto.getUserName() != null)
                update.set("userName", studentDto.getUserName());
            if (studentDto.getEmail() != null)
                update.set("email", studentDto.getEmail());
            if (studentDto.getPhone() != null)
                update.set("phone", studentDto.getPhone());
            if (studentDto.getGroupName() != null)
                update.set("groupName", studentDto.getGroupName());
            if (studentDto.getFacebook() != null)
                update.set("facebook", studentDto.getFacebook());

            Query query = new Query(Criteria.where("studentId").is(studentDto.getStudentId()));
            bulkOps.upsert(query, update);
        }

        BulkWriteResult result = bulkOps.execute();
        return result.getModifiedCount() + result.getUpserts().size();
    }

    public @NotNull List<Student> findAllWithFilterAndPagination(
            @NotNull Student studentFilter, int page, int size) {
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
