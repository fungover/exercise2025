package di_lab.Repository;

public class InMemoryUserRepository implements UserRepository {
    @Override
    public void save(String username) {
        System.out.println("Saving user " + username + " in memory");
    }
}
