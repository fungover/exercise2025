package di_lab.Service;

import di_lab.Repository.UserRepository;

public class UserServiceImpl implements UserService{
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void register(String username) {
        System.out.println("Register user: " + username);
        repository.save(username);
    }
}
