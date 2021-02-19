package com.tonghoangvu.lhufriends.entity;

import com.tonghoangvu.lhufriends.common.Gender;
import com.tonghoangvu.lhufriends.common.UserRole;
import com.tonghoangvu.lhufriends.model.request.UserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
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

    public UserEntity(@NotNull UserRequest userRequest) {
        this.username = userRequest.getUsername();
        this.password = userRequest.getPassword();
        this.displayName = userRequest.getDisplayName();
        this.gender = userRequest.getGender();
        this.bio = userRequest.getBio();
        this.birthday = userRequest.getBirthday();
        this.email = userRequest.getEmail();
        this.phone = userRequest.getPhone();
    }
}
