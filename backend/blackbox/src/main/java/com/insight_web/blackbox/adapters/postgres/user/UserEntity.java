package com.insight_web.blackbox.adapters.postgres.user;

import com.insight_web.blackbox.domain.Role;
import com.insight_web.blackbox.domain.User;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    void fillForSaving(User user) {
        if (user.getId() != null) {
            id = user.getId();
        }
        password = user.getPassword();
        email = user.getEmail();
    }

    User toDomainModel() {
        var user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(Set.of(Role.ADMIN));
        return user;
    }
}
