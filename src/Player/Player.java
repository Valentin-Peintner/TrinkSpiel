package Player;

public class Player implements Scorable {
    private String name;
    private int points;

    // Konstruktor
    public Player(String name) {
        this.name = name;
        this.points = 0; // Punkte initial auf 0 setzen
    }

    // Getter für den Namen
    public String getName() {
        return name;
    }

    // Getter für die Punkte
    public int getPoints() {
        return points;
    }

    // Methode zum Hinzufügen von Punkten
    public void addPoints(int additionalPoints) {
        this.points += additionalPoints;
    }
}
