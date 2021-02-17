package com.tonghoangvu.lhufriends.entity;

import com.tonghoangvu.lhufriends.common.Gender;
import com.tonghoangvu.lhufriends.common.UserRole;
import com.tonghoangvu.lhufriends.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
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

    public User(@NotNull UserDto userDto) {
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.displayName = userDto.getDisplayName();
        this.gender = userDto.getGender();
        this.bio = userDto.getBio();
        this.birthday = userDto.getBirthday();
        this.email = userDto.getEmail();
        this.phone = userDto.getPhone();
    }
}
