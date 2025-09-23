package dungeoncrawler.enteties;

public class Giant extends Enemy{
    public Giant(int[] position){
        this.position = position;
        setHp(40);
        setStrength(11);
    }
}
