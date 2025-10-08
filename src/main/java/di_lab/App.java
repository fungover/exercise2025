package di_lab;

import di_lab.Repository.InMemoryUserRepository;
import di_lab.Repository.UserRepository;
import di_lab.Service.UserService;
import di_lab.Service.UserServiceImpl;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class App {
    public static void main(String[] args) {
        Weld weld = new Weld();

        try (WeldContainer container = weld.initialize()) {
            UserService service = container.select(UserService.class).get();
            service.register("Batman");
        }
    }
}
