package di_lab;

import di_lab.Repository.InMemoryUserRepository;
import di_lab.Repository.UserRepository;
import di_lab.Service.UserService;
import di_lab.Service.UserServiceImpl;

public class App {
    public static void main(String[] args) {

        // Create repository
        UserRepository userRepository = new InMemoryUserRepository();

        // Create service and inject dependency via the contructor. Here you can just change the argument
        // in the constructor if you want to change repository or a mocked repository.
        UserService userService = new UserServiceImpl(userRepository);

        // Use service
        userService.register("Batman");
        userService.register("Robin");
    }
}
