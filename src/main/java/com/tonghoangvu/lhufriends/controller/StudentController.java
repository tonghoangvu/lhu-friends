package com.tonghoangvu.lhufriends.controller;

import com.tonghoangvu.lhufriends.common.Const;
import com.tonghoangvu.lhufriends.common.ErrorCode;
import com.tonghoangvu.lhufriends.entity.Student;
import com.tonghoangvu.lhufriends.exception.AppException;
import com.tonghoangvu.lhufriends.model.StudentItem;
import com.tonghoangvu.lhufriends.model.request.StudentFilter;
import com.tonghoangvu.lhufriends.model.request.UpsertRequest;
import com.tonghoangvu.lhufriends.model.response.UpsertResponse;
import com.tonghoangvu.lhufriends.service.StudentService;
import com.tonghoangvu.lhufriends.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final @NotNull StudentService studentService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/upsert")
    public @NotNull ResponseEntity<UpsertResponse> upsertStudentList(
            @Valid @RequestBody @NotNull UpsertRequest upsertRequest,
            @NotNull BindingResult bindingResult) {
        ControllerUtil.handleBindingError(bindingResult);
        UpsertResponse upsertResponse = studentService.upsertStudentList(upsertRequest);
        return ResponseEntity.ok(upsertResponse);
    }

    @PostMapping("/")
    public @NotNull ResponseEntity<List<StudentItem>> getStudentList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestBody @NotNull StudentFilter studentFilter) {
        if (size > Const.MAX_STUDENTS_PER_REQUEST.getIntValue())
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_TOO_MANY,
                    "Max students per request is " + Const.MAX_STUDENTS_PER_REQUEST.getIntValue());
        List<Student> studentList = studentService.getStudentList(studentFilter, page, size);
        List<StudentItem> studentItemList = studentList.stream()
                .map(StudentItem::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(studentItemList);
    }
}
