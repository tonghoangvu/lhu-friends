package com.tonghoangvu.lhufriends.model;

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
    private String username;
    private String displayName;
    private Set<UserRole> roles;
    private Date createdAt;
    private boolean deleted;

    public UserModel(User entity) {
        this.username = entity.getUsername();
        this.displayName = entity.getDisplayName();
        this.roles = entity.getRoles();
        this.createdAt = entity.getCreatedAt();
        this.deleted = entity.isDeleted();
    }
}
