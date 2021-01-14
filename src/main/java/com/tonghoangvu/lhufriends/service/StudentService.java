package com.tonghoangvu.lhufriends.service;

import com.tonghoangvu.lhufriends.dto.StudentDto;
import com.tonghoangvu.lhufriends.dto.UpsertDto;
import com.tonghoangvu.lhufriends.entity.Student;
import com.tonghoangvu.lhufriends.model.UpsertModel;
import com.tonghoangvu.lhufriends.repository.CustomStudentRepository;
import com.tonghoangvu.lhufriends.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final CustomStudentRepository customStudentRepository;

    public UpsertModel upsertStudentList(UpsertDto upsertDto) {
        long startTime = new Date().getTime();
        List<String> skippedIds = upsertDto.getData().stream()
                .map(StudentDto::getStudentId)
                .collect(Collectors.toList());

        List<Student> studentList = new ArrayList<>();
        for (StudentDto studentDto: upsertDto.getData()) {
            // Find exists Student in DB or create a new one
            Student student = studentRepository.findFirstByStudentId(studentDto.getStudentId());
            if (student == null) {
                student = new Student();
                student.setCreatedAt(new Date());
            }

            // Override data
            student.loadDto(studentDto);
            student.setUpdatedAt(new Date());

            // Remove skippedIds
            skippedIds.remove(studentDto.getStudentId());
        }
        studentRepository.saveAll(studentList);

        return UpsertModel.builder()
                .time(new Date().getTime() - startTime)
                .skipped(skippedIds)
                .build();
    }

    public List<Student> getStudentList(Student studentFilter, int page, int size) {
        List<Student> studentList = customStudentRepository
                .findAllWithFilterAndPagination(studentFilter, page, size);
        if (studentFilter.getPhone() == null || studentFilter.getPhone().isEmpty())
            studentList.forEach(student -> student.setPhone(null));
        return studentList;
    }
}
