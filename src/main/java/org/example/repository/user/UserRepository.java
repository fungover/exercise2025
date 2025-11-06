package org.example.repository.user;

import org.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);
    Optional<User> findByApiKey(String apiKey);
}
