package com.tonghoangvu.lhufriends.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "students")
public class StudentEntity {
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

    /* CẢNH BÁO
     * Do dùng upsert để ghi vào DB, nên class này chỉ dùng để đánh dấu cấu trúc Entity
     * Thực tế thì Model sẽ tạo ra Update command vào thẳng DB, nên sẽ không thông qua Entity
     */
}
