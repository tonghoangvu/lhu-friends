package com.tonghoangvu.lhufriends.model;

import com.tonghoangvu.lhufriends.entity.Student;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Size;

@Getter
@Setter
public class StudentItem {
    @Size(min = 9, max = 9, message = "Student id is invalid")
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

    @Contract(pure = true)
    public StudentItem(@NotNull Student entity) {
        this.studentId = entity.getStudentId();
        this.fullName = entity.getFullName();
        this.birthday = entity.getBirthday();
        this.gender = entity.getGender();
        this.placeOfBirth = entity.getPlaceOfBirth();
        this.ethnic = entity.getEthnic();
        this.nationality = entity.getNationality();
        this.classId = entity.getClassId();
        this.image = entity.getImage();
        this.avatar = entity.getAvatar();
        this.userName = entity.getUserName();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
        this.groupName = entity.getGroupName();
        this.facebook = entity.getFacebook();
    }
}
