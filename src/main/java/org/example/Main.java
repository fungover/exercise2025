package org.example;

import org.example.repository.FakeUserRepository;
import org.example.repository.LocalUserRepository;
import org.example.repository.UserRepository;
import org.example.service.UserRegistrationService;
import org.example.service.UserRegistrationServiceEncrypted;
import org.example.users.User;

import java.security.NoSuchAlgorithmException;

public class Main {
  public static void main(String[] args) throws NoSuchAlgorithmException {

    UserRepository localRepository = new LocalUserRepository();
    UserRepository fakeRepository = new FakeUserRepository();

    UserRegistrationService userRegistrationService = new UserRegistrationServiceEncrypted(localRepository);
    UserRegistrationService userRegistrationService2 = new UserRegistrationServiceEncrypted(fakeRepository);

    userRegistrationService.registerUser(new User("Test", "12345"));

    localRepository.login("Test", "12345");
    localRepository.login("Test", "12354");

    userRegistrationService2.registerUser(new User("Test2", "12345"));
    fakeRepository.login("Test", "12345");
  }
}