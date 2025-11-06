package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.*;
import org.example.entity.ApiEntity;
import org.example.entity.UserEntity;
import org.example.repository.ApiRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.utils.BCryptUtil.checkPassword;
import static org.example.utils.BCryptUtil.hashPassword;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final ApiRepository apiRepository;

  public UserService(UserRepository userRepository, ApiRepository apiRepository) {
    this.userRepository = userRepository;
    this.apiRepository = apiRepository;
  }

  @Transactional
  public UserNew addNewUser(User user) {
    if (userRepository.existsByEmail(user.email())) {
      throw new EntityNotFoundException("User with email: " + user.email() + " already exists");
    }
    var newUser = userRepository.save(new UserEntity(
            null,
            user.name(),
            hashPassword(user.password()),
            user.email(),
            null));

    var api = apiRepository.save(new ApiEntity(null, newUser, null, 0L , null));

    return new UserNew(
            newUser.getId(),
            newUser.getName(),
            newUser.getEmail(),
            api.getApiKey()
    );
  }

  @Transactional(readOnly = true)
  public UserPublic getUserById(Long id) {
    UserEntity user = userRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

    return new UserPublic(
            user.getName(),
            user.getEmail()
    );
  }

  @Transactional(readOnly = true)
  public UserNotes findUserAndGetNotes(String email, String password) {
    UserEntity user = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

    if(checkPassword(password, user.getPassword())){
      return new UserNotes(
              user.getId(),
              user.getName(),
              user.getNotes().stream().map(note -> new Note(
                      note.getId(),
                      note.getValue(),
                      note.getUser().getId(),
                      note.getCreatedAt())).toList());
    }else{
      throw new EntityNotFoundException("Invalid credentials");
    }
  }

  public UserApi getUserApiByEmailAndPassword(String email, String password) {
    UserEntity user = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

    if(checkPassword(password, user.getPassword())){
      return new UserApi(
              user.getApiKey().getApiKey(),
              user.getApiKey().getCounter(),
              user.getApiKey().getLastUsedAt()
      );
    }else{
      throw new EntityNotFoundException("Invalid credentials");
    }
  }
}
