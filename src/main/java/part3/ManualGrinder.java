package part3;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ManualGrinder implements BeanGrinder {

    @Override
    public String grind(String beans) {
        return beans + " med en handdriven kvarn, pust!";
    }
}
