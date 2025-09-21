package org.example;

import org.example.repository.FakeUserRepository;
import org.example.repository.LocalUserRepository;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.example.service.UserServiceManager;

import java.security.NoSuchAlgorithmException;

public class Main {
  public static void main(String[] args) throws NoSuchAlgorithmException {

    UserRepository localRepository = new LocalUserRepository();
    UserRepository fakeRepository = new FakeUserRepository();

    UserService userService = new UserServiceManager(localRepository);
    UserService userService2 = new UserServiceManager(fakeRepository);

    userService.registerUser(new User("Test", "12345"));

    localRepository.login("Test", "12345");
    localRepository.login("Test", "12354");

    userService2.registerUser(new User("Test2", "12345"));
    fakeRepository.login("Test", "12345");
  }
}