package com.tonghoangvu.lhufriends.entity;

import com.tonghoangvu.lhufriends.dto.StudentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "students")
public class Student {
    private String id;
    private Date createdAt;
    private Date updatedAt;

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

    public Student(@NotNull StudentDto dto) {
        this.studentId = dto.getStudentId();
        this.fullName = dto.getFullName();
        this.birthday = dto.getBirthday();
        this.gender = dto.getGender();
        this.placeOfBirth = dto.getPlaceOfBirth();
        this.ethnic = dto.getEthnic();
        this.nationality = dto.getNationality();
        this.classId = dto.getClassId();
        this.image = dto.getImage();
        this.avatar = dto.getAvatar();
        this.userName = dto.getUserName();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.groupName = dto.getGroupName();
        this.facebook = dto.getFacebook();
    }
}
