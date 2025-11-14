package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.token.TokenRequest;
import org.example.dto.user.User;
import org.example.dto.user.UserNew;
import org.example.entity.TokenEntity;
import org.example.entity.UserEntity;
import org.example.exceptionHandler.ConflictException;
import org.example.repository.TokenRepository;
import org.example.repository.UserRepository;
import org.example.utils.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
  public UserNew registerUser(User user) {
    if (userRepository.existsByEmail(user.email())) {
      throw new ConflictException(user.email());
    }

    var newUser = userRepository.save(new UserEntity(
            null,
            user.name(),
            hashPassword(user.password()),
            user.email(),
            null));

    var api = tokenRepository.save(new TokenEntity(null, newUser, jwtUtil.generateToken(newUser.getEmail(), 1000*60*5L), jwtUtil.generateToken(newUser.getEmail(), 1000*60*60*7*24L),  0L ,  LocalDateTime.now()));

    return new UserNew(
            newUser.getId(),
            newUser.getName(),
            newUser.getEmail(),
            api.getToken(),
            api.getRefreshToken()
    );
  }

  public TokenRequest loginUser(String email, String password) {
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
      tokenEntity.setLastUsedAt(LocalDateTime.now());
      tokenRepository.save(tokenEntity);

      return new TokenRequest(newAccessToken, newRefreshToken);
    }else{
      throw new EntityNotFoundException("Invalid credentials");
    }
  }
}
