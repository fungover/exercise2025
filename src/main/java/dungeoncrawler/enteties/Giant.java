package dungeoncrawler.enteties;

public class Giant extends Enemy{
    public Giant(int[] position){
        this.position = position;
        this.hp = 30;
        this.strength = 11;
        this.damage = 0;
    }
}
