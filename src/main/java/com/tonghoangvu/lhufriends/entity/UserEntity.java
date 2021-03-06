package com.tonghoangvu.lhufriends.entity;

import com.tonghoangvu.lhufriends.common.Gender;
import com.tonghoangvu.lhufriends.common.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "users")
public class UserEntity {
    private String id;
    private Date createdAt;
    private Date updatedAt;
    private Set<UserRole> roles;
    private boolean deleted;

    private String username;
    private String password;
    private String displayName;
    private Gender gender;
    private String bio;
    private Date birthday;
    private String email;
    private String phone;
}
