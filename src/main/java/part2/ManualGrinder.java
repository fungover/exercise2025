package part2;

import part2.BeanGrinder;

public class ManualGrinder implements BeanGrinder {
    @Override
    public String grind(String beans) {
        return beans + " med en handdriven kvarn, pust!";
    }
}
