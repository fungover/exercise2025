package dungeoncrawler.enteties;

public class Troll extends Enemy{
    public Troll(int[] position){
        this.position = position;
        setHp(30);
        setStrength(8);
    }
}
