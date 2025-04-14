package com.insight_web.blackbox.usecase.storage;

import com.insight_web.blackbox.domain.User;

public interface UserStorage {
    User create(User user);

    User findById(Long id);

    User findByEmail(String email);

    Boolean existsByEmail(String email);
}
