package di_lab.Repository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InMemoryUserRepository implements UserRepository {

    @Override
    public void save(String username) {
        System.out.println("Saving user " + username + " in memory");
    }
}
