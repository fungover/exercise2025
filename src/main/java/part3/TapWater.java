package part3;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TapWater implements WaterSource {

    @Override
    public String getWater() {
        return "vatten från kranen";
    }
}
