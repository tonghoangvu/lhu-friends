package com.tonghoangvu.lhufriends.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class StudentDto {
    @Size(min = 9, max = 9, message = "Student id is not valid")
    private String studentId;
    private String fullName;
    private String birthday;
    private String gender;
    private String placeOfBirth;
    private String ethnic;
    private String nationality;
    private String classId;
    private String image;
    private String avatar;
    private String userName;
    private String email;
    private String phone;
    private String groupName;
    private String facebook;
}
