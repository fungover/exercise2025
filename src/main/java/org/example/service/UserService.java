package org.example.service;

import org.example.dto.User;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public User addNewUser(User user) {
    var newUser = userRepository.save(new UserEntity(
            null,
            user.name(),
            user.password(),
            user.email(),
            null));
    return new User(
            newUser.getId(),
            newUser.getName(),
            newUser.getPassword(),
            newUser.getEmail(),
            null
    );
  }
}
