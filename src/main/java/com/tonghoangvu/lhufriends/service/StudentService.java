package com.tonghoangvu.lhufriends.service;

import com.tonghoangvu.lhufriends.entities.Student;
import com.tonghoangvu.lhufriends.repository.CustomStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final CustomStudentRepository customStudentRepository;

    public List<Student> getStudentList(Student studentFilter, int page, int size) {
        List<Student> studentList = customStudentRepository
                .findAllWithFilterAndPagination(studentFilter, page, size);
        if (studentFilter.getPhone() == null || studentFilter.getPhone().isEmpty())
            studentList.forEach(student -> student.setPhone(null));
        return studentList;
    }
}
