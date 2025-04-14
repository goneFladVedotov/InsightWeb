package com.insight_web.blackbox.domain;

import lombok.Data;

import java.util.Set;

@Data
public class User {
    private Long id;
    private String email;
    private String password;
    private Set<Role> roles;
}
