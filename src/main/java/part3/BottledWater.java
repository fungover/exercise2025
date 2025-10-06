package part3;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BottledWater implements WaterSource {

    @Override
    public String getWater() {
        return "finvatten på flaska";
    }
}
