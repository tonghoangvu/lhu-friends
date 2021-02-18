package com.tonghoangvu.lhufriends.dto.response;

import com.tonghoangvu.lhufriends.common.Gender;
import com.tonghoangvu.lhufriends.common.UserRole;
import com.tonghoangvu.lhufriends.entity.User;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Set;

@Getter
public class UserInfoDto {
    private final Date createdAt;
    private final Date updatedAt;
    private final Set<UserRole> roles;
    private final boolean deleted;

    private final String username;
    private final String displayName;
    private final Gender gender;
    private final String bio;
    private final Date birthday;
    private final String email;
    private final String phone;

    public UserInfoDto(@NotNull User entity) {
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
