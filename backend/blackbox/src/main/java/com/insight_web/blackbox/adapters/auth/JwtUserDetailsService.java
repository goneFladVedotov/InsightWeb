package com.insight_web.blackbox.adapters.auth;

import com.insight_web.blackbox.adapters.postgres.user.UserRepository;
import com.insight_web.blackbox.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}