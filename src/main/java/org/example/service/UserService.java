package org.example.service;

import org.example.model.User;

import java.util.List;

// metoderna ska implementeras i UserServiceImpl
public interface UserService {
        List<User> getAllUsers();
        User createUser(User user);
        User getUserById(Long id);
}
