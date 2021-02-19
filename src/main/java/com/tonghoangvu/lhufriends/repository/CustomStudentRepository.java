package com.tonghoangvu.lhufriends.repository;

import com.mongodb.bulk.BulkWriteResult;
import com.tonghoangvu.lhufriends.entity.Student;
import com.tonghoangvu.lhufriends.model.StudentItem;
import com.tonghoangvu.lhufriends.model.request.StudentFilter;
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

    public int upsertStudentList(@NotNull List<StudentItem> studentItemList) {
        // Create a list of bulk operations
        BulkOperations bulkOps = mongoOperations.bulkOps(
                BulkOperations.BulkMode.UNORDERED, Student.class);

        for (StudentItem studentItem : studentItemList) {
            // Update not null fields
            Update update = new Update()
                    .currentDate("updatedAt")
                    .setOnInsert("createdAt", new Date());
            if (studentItem.getStudentId() != null)
                update.set("studentId", studentItem.getStudentId());
            if (studentItem.getFullName() != null)
                update.set("fullName", studentItem.getFullName());
            if (studentItem.getBirthday() != null)
                update.set("birthday", studentItem.getBirthday());
            if (studentItem.getGender() != null)
                update.set("gender", studentItem.getGender());
            if (studentItem.getPlaceOfBirth() != null)
                update.set("placeOfBirth", studentItem.getPlaceOfBirth());
            if (studentItem.getEthnic() != null)
                update.set("ethnic", studentItem.getEthnic());
            if (studentItem.getNationality() != null)
                update.set("nationality", studentItem.getNationality());
            if (studentItem.getClassId() != null)
                update.set("classId", studentItem.getClassId());
            if (studentItem.getImage() != null)
                update.set("image", studentItem.getImage());
            if (studentItem.getAvatar() != null)
                update.set("avatar", studentItem.getAvatar());
            if (studentItem.getUserName() != null)
                update.set("userName", studentItem.getUserName());
            if (studentItem.getEmail() != null)
                update.set("email", studentItem.getEmail());
            if (studentItem.getPhone() != null)
                update.set("phone", studentItem.getPhone());
            if (studentItem.getGroupName() != null)
                update.set("groupName", studentItem.getGroupName());
            if (studentItem.getFacebook() != null)
                update.set("facebook", studentItem.getFacebook());

            Query query = new Query(Criteria.where("studentId").is(studentItem.getStudentId()));
            bulkOps.upsert(query, update);
        }

        BulkWriteResult result = bulkOps.execute();
        return result.getModifiedCount() + result.getUpserts().size();
    }

    public @NotNull List<Student> findAllWithFilterAndPagination(
            @NotNull StudentFilter studentFilter, int page, int size) {
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
