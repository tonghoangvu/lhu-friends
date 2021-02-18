package com.tonghoangvu.lhufriends.service;

import com.tonghoangvu.lhufriends.entity.Student;
import com.tonghoangvu.lhufriends.model.request.StudentFilter;
import com.tonghoangvu.lhufriends.model.request.UpsertRequest;
import com.tonghoangvu.lhufriends.model.response.UpsertResponse;
import com.tonghoangvu.lhufriends.repository.CustomStudentRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final @NotNull CustomStudentRepository customStudentRepository;

    public @NotNull UpsertResponse upsertStudentList(@NotNull UpsertRequest upsertRequest) {
        long startTime = new Date().getTime();
        long upsertedCount = customStudentRepository.upsertStudentList(upsertRequest.getData());
        return new UpsertResponse(new Date().getTime() - startTime, upsertedCount);
    }

    public @NotNull List<Student> getStudentList(
            @NotNull StudentFilter studentFilter, int page, int size) {
        List<Student> studentList = customStudentRepository
                .findAllWithFilterAndPagination(studentFilter, page, size);
        if (studentFilter.getPhone() == null || studentFilter.getPhone().isEmpty())
            studentList.forEach(student -> student.setPhone(null));
        return studentList;
    }
}
