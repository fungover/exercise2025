package dungeoncrawler.enteties;

public class Troll extends Enemy{
    public Troll(int[] position){
        this.position = position;
        this.hp = 30;
        this.strength = 8;
        this.damage = 0;
    }
}
