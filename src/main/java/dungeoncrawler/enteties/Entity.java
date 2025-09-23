package dungeoncrawler.enteties;

public class Entity {
    int[] position;

    public int[] getPosition() {
        return position == null ? null : position.clone();
    }
    public void setPosition(int[] position) {
        this.position = position == null ? null : position.clone();
    }
}
