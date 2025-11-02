package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.Note;
import org.example.dto.User;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.example.utils.BCryptUtil.hashPassword;

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
            hashPassword(user.password()),
            user.email(),
            null));
    return new User(
            newUser.getId(),
            newUser.getName(),
            "hashedPassword",
            newUser.getEmail(),
            null
    );
  }

  public User getUserById(Long id) {
    UserEntity user = userRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

    return new User(
            user.getId(),
            user.getName(),
            null,
            user.getEmail(),
            user.getNotes().stream().map(note -> new Note(
                    note.getId(),
                    note.getValue(),
                    note.getUser().getId(),
                    note.getCreatedAt())).toList()
    );
  }
}
