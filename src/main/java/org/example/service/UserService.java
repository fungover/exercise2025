package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.Note;
import org.example.dto.User;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.utils.BCryptUtil.checkPassword;
import static org.example.utils.BCryptUtil.hashPassword;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public User addNewUser(User user) {
    if (userRepository.existsByEmail(user.email())) {
      throw new EntityNotFoundException("User with email: " + user.email() + " already exists");
    }
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

  @Transactional(readOnly = true)
  public User getUserById(Long id) {
    UserEntity user = userRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

    return new User(
            user.getId(),
            user.getName(),
            null,
            user.getEmail(),
            null
    );
  }

  @Transactional(readOnly = true)
  public User findUserAndGetNotes(String email, String password) {
    UserEntity user = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

    if(checkPassword(password, user.getPassword())){
      return new User(
              user.getId(),
              user.getName(),
              null,
              user.getEmail(),
              user.getNotes().stream().map(note -> new Note(
                      note.getId(),
                      note.getValue(),
                      note.getUser().getId(),
                      note.getCreatedAt())).toList());
    }else{
      throw new EntityNotFoundException("Invalid credentials");
    }
  }
}
