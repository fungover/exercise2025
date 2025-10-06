package part2;

import part2.WaterSource;

public class BottledWater implements WaterSource {
    @Override
    public String getWater() {
        return "finvatten på flaska";
    }
}
