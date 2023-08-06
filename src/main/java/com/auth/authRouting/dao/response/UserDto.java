package com.auth.authRouting.dao.response;

import com.auth.authRouting.model.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
