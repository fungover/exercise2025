package org.example.repository;

import org.example.entities.CustomizedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CustomizedUser, Integer> {
    Optional<CustomizedUser> findByUserName(String username);
}
