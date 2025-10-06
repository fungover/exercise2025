package part3;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ElectricGrinder implements BeanGrinder {

    @Override
    public String grind(String beans) {
        return beans + " med en elektrisk kvarn Wow!";
    }
}