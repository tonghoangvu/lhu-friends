package com.tonghoangvu.lhufriends.model.response;

import com.tonghoangvu.lhufriends.common.Gender;
import com.tonghoangvu.lhufriends.common.UserRole;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Setter
public class UserInfo {
    private String id;
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
}
