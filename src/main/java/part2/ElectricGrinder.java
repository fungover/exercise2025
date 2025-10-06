package part2;

public class ElectricGrinder implements BeanGrinder {
    @Override
    public String grind(String beans) {
        return beans + " med en elektrisk kvarn" ;
    }
}
