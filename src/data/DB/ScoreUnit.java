package data.DB;

public class ScoreUnit {

    String name;
    int points;

    public ScoreUnit(String name, int points) {
        this.points = points;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name + " " + points;
    }
}
