package com.insight_web.blackbox.adapters.postgres.user;

import com.insight_web.blackbox.domain.User;
import com.insight_web.blackbox.domain.exception.ResourceNotFoundException;
import com.insight_web.blackbox.usecase.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserStorageImpl implements UserStorage {
    private final UserRepository userRepository;
    @Override
    public User create(User user) {
        var entity = new UserEntity();
        entity.fillForSaving(user);
        return userRepository.save(entity).toDomainModel();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow().toDomainModel();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user by email " + email + "not found"))
                .toDomainModel();
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
