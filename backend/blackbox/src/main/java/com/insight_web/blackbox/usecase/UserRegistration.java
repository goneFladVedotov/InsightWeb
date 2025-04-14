package com.insight_web.blackbox.usecase;

import com.insight_web.blackbox.domain.Role;
import com.insight_web.blackbox.domain.User;
import com.insight_web.blackbox.domain.exception.ResourceNotFoundException;
import com.insight_web.blackbox.usecase.action.RegisterAction;
import com.insight_web.blackbox.usecase.storage.UserStorage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRegistration {
    private final UserStorage userStorage;

    @Transactional
    public void register(RegisterAction action) {
        if (!action.getPassword().equals(action.getPasswordConfirmation())) {
            throw new RuntimeException("Password and password does not match");
        }
        if (!userStorage.existsByEmail(action.getEmail())) {
            throw new ResourceNotFoundException("user by email " + action.getEmail() + "does not found");
        }
        var user = action.toDomainModel();

        userStorage.create(user);
    }
}
