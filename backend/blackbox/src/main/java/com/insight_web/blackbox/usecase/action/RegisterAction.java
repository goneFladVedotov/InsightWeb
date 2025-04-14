package com.insight_web.blackbox.usecase.action;

import com.insight_web.blackbox.domain.Role;
import com.insight_web.blackbox.domain.User;
import lombok.Data;

import java.util.HashSet;
import java.util.List;

@Data
public class RegisterAction {
    private String email;
    private String password;
    private String passwordConfirmation;

    public User toDomainModel() {
        var user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(new HashSet<>(List.of(Role.ADMIN)));
        return user;
    }
}
