package com.tonghoangvu.lhufriends.service;

import com.tonghoangvu.lhufriends.dto.UpsertDto;
import com.tonghoangvu.lhufriends.entity.Student;
import com.tonghoangvu.lhufriends.model.UpsertModel;
import com.tonghoangvu.lhufriends.repository.CustomStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final CustomStudentRepository customStudentRepository;

    public UpsertModel upsertStudentList(UpsertDto upsertDto) {
        long startTime = new Date().getTime();
        long upserted = customStudentRepository.upsertStudentList(upsertDto.getData());
        return UpsertModel.builder()
                .time(new Date().getTime() - startTime)
                .upserted(upserted)
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
