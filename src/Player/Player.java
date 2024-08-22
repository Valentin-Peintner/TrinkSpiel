package Player;

public class Player implements Scorable {
    private String name;
    private int points;

    public Player(String name) {
        this.name = name;
        this.points = 0;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int additionalPoints) {
        this.points += additionalPoints;
    }
}
