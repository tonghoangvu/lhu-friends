package com.tonghoangvu.lhufriends.entity;

import com.tonghoangvu.lhufriends.common.UserRole;
import com.tonghoangvu.lhufriends.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private Boolean deleted;

    private String username;
    private String password;
    private String displayName;

    public User(UserDto userDto) {
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.displayName = userDto.getDisplayName();
    }
}
