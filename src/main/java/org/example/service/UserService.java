package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.note.NoteResponse;
import org.example.dto.token.TokenRequest;
import org.example.dto.token.UpdatedToken;
import org.example.dto.user.User;
import org.example.dto.user.UserApi;
import org.example.dto.user.UserNew;
import org.example.dto.user.UserNotes;
import org.example.entity.TokenEntity;
import org.example.entity.UserEntity;
import org.example.repository.TokenRepository;
import org.example.repository.UserRepository;
import org.example.utils.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.example.utils.BCryptUtil.checkPassword;
import static org.example.utils.BCryptUtil.hashPassword;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;
  private final JwtUtil jwtUtil;

  public UserService(UserRepository userRepository, TokenRepository tokenRepository, JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.tokenRepository = tokenRepository;
    this.jwtUtil = jwtUtil;
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

    var api = tokenRepository.save(new TokenEntity(null, newUser, jwtUtil.generateToken(newUser.getEmail(), 1000*60*5L), jwtUtil.generateToken(newUser.getEmail(), 1000*60*60*7*24L),  0L , null));

    return new UserNew(
            newUser.getId(),
            newUser.getName(),
            newUser.getEmail(),
            api.getToken(),
            api.getRefreshToken()
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

  public TokenRequest login(String email, String password) {
    UserEntity user = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

    if(checkPassword(password, user.getPassword())){
      var tokenEntity = tokenRepository.findByToken(user.getToken().getToken())
              .orElseThrow(() -> new EntityNotFoundException("Token not found"));

      String newAccessToken = jwtUtil.generateToken(tokenEntity.getUser().getEmail(), 1000 * 60 * 5L);
      String newRefreshToken = jwtUtil.generateToken(tokenEntity.getUser().getEmail(), 1000 * 60 * 60 * 7 * 24L);
      tokenEntity.setToken(newAccessToken);
      tokenEntity.setRefreshToken(newRefreshToken);
      tokenRepository.save(tokenEntity);

      return new TokenRequest(newAccessToken, newRefreshToken);
    }else{
      throw new EntityNotFoundException("Invalid credentials");
    }
  }
}
