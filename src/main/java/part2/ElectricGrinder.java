package part2;

import part2.BeanGrinder;

public class ElectricGrinder implements BeanGrinder {
    @Override
    public String grind(String beans) {
        return beans + " med en elektrisk kvarn" ;
    }
}
