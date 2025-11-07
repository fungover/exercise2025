package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.note.NoteResponse;
import org.example.dto.user.User;
import org.example.dto.user.UserApi;
import org.example.dto.user.UserNew;
import org.example.dto.user.UserNotes;
import org.example.entity.ApiEntity;
import org.example.entity.UserEntity;
import org.example.repository.ApiRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  public UserNotes findUserAndGetNotes(String email) {
    UserEntity user = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

      return new UserNotes(
              user.getName(),
              user.getNotes().stream().filter(note -> note.getDeletedAt() == null)
                      .map(note -> new NoteResponse(
                      note.getId(),
                      note.getValue())).toList());

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
