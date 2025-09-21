package org.example;

import org.example.repository.FakeUserRepository;
import org.example.repository.LocalUserRepository;
import org.example.repository.UserRepository;
import org.example.service.UserServiceRegistration;
import org.example.service.UserServiceRegistrationEncrypted;
import org.example.users.User;

import java.security.NoSuchAlgorithmException;

public class Main {
  public static void main(String[] args) throws NoSuchAlgorithmException {

    UserRepository localRepository = new LocalUserRepository();
    UserRepository fakeRepository = new FakeUserRepository();

    UserServiceRegistration userServiceRegistration = new UserServiceRegistrationEncrypted(localRepository);
    UserServiceRegistration userServiceRegistration2 = new UserServiceRegistrationEncrypted(fakeRepository);

    userServiceRegistration.registerUser(new User("Test", "12345"));

    localRepository.login("Test", "12345");
    localRepository.login("Test", "12354");

    userServiceRegistration2.registerUser(new User("Test2", "12345"));
    fakeRepository.login("Test", "12345");
  }
}