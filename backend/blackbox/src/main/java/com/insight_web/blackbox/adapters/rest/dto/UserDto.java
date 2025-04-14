package com.insight_web.blackbox.adapters.rest.dto;

import com.insight_web.blackbox.domain.Role;
import com.insight_web.blackbox.domain.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private List<Role> roles;
}
