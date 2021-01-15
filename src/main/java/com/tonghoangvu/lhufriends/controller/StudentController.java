package com.tonghoangvu.lhufriends.controller;

import com.tonghoangvu.lhufriends.common.Const;
import com.tonghoangvu.lhufriends.common.ErrorCode;
import com.tonghoangvu.lhufriends.dto.UpsertDto;
import com.tonghoangvu.lhufriends.entity.Student;
import com.tonghoangvu.lhufriends.exception.AppException;
import com.tonghoangvu.lhufriends.model.UpsertModel;
import com.tonghoangvu.lhufriends.service.StudentService;
import com.tonghoangvu.lhufriends.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/upsert")
    public ResponseEntity<UpsertModel> upsertStudentList(
            @RequestBody UpsertDto upsertDto,
            final BindingResult bindingResult) {
        ControllerUtil.handleBindingError(bindingResult);
        return ResponseEntity.ok(studentService.upsertStudentList(upsertDto));
    }

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
