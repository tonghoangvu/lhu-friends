package com.tonghoangvu.lhufriends.model.request;

import com.tonghoangvu.lhufriends.common.Gender;
import com.tonghoangvu.lhufriends.util.validator.NullOrNotEmpty;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.tonghoangvu.lhufriends.common.ValidationProfile.OnCreate;
import static com.tonghoangvu.lhufriends.common.ValidationProfile.OnUpdate;

@Getter
public class UserRequest {
    @NotEmpty(message = "Username is required", groups = { OnCreate.class })
    @NullOrNotEmpty(message = "Username is required", groups = { OnUpdate.class })
    private String username;

    @NotEmpty(message = "Password is required", groups = { OnCreate.class })
    @NullOrNotEmpty(message = "Password is required", groups = { OnUpdate.class })
    @Min(value = 8, message = "Password must be longer than 8 letters")
    // Nếu null thì @Min không hoạt động, nên không cần OnUpdate
    private String password;

    @NotEmpty(message = "Display name is required", groups = { OnCreate.class })
    @NullOrNotEmpty(message = "Display name is required", groups = { OnUpdate.class })
    private String displayName;

    @NotNull(message = "Gender is invalid", groups = { OnCreate.class })
    // Do not using @NullOrNotEmpty on non-string fields
    // Nếu không phải String thì sẽ không check @NotEmpty khi null
    private Gender gender;

    private String bio;
    private Date birthday;

    @Email(message = "Email is invalid", groups = { OnCreate.class, OnUpdate.class })
    private String email;

    private String phone;
}
