package com.tonghoangvu.lhufriends.controllers;

import com.tonghoangvu.lhufriends.common.Const;
import com.tonghoangvu.lhufriends.entities.Student;
import com.tonghoangvu.lhufriends.exception.AppException;
import com.tonghoangvu.lhufriends.common.ErrorCode;
import com.tonghoangvu.lhufriends.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/")
    public ResponseEntity<List<Student>> getStudentList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestBody Student studentFilter) {
        if (size > Const.MAX_STUDENTS_PER_REQUEST.getIntValue())
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_TOO_MANY,
                    "Max students per request is " + Const.MAX_STUDENTS_PER_REQUEST.getIntValue());
        List<Student> studentList = studentService.getStudentList(studentFilter, page, size);
        return ResponseEntity.ok(studentList);
    }
}
