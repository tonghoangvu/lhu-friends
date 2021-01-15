package com.tonghoangvu.lhufriends.model;

import com.tonghoangvu.lhufriends.common.Gender;
import com.tonghoangvu.lhufriends.common.UserRole;
import com.tonghoangvu.lhufriends.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserModel {
    private Date createdAt;
    private Date updatedAt;
    private Set<UserRole> roles;
    private boolean deleted;

    private String username;
    private String displayName;
    private Gender gender;
    private String bio;
    private Date birthday;
    private String email;
    private String phone;

    public UserModel(User entity) {
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.roles = entity.getRoles();
        this.deleted = entity.isDeleted();

        this.username = entity.getUsername();
        this.displayName = entity.getDisplayName();
        this.gender = entity.getGender();
        this.bio = entity.getBio();
        this.birthday = entity.getBirthday();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
    }
}
