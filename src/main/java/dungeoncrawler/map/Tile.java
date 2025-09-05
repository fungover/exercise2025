package dungeoncrawler.map;

public class Tile {
    String type;
    int[] position;

    public Tile(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public int[] getPosition() {
        return position == null ? null : position.clone();
    }
    public void setPosition(int[] position) {
        this.position = position == null ? null : position.clone();
    }
}
