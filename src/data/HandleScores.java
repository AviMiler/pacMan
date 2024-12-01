package data;

import data.DB.ScoreUnit;
import data.ineerDB.LinkedList;

public class HandleScores {

    public static ScoreUnit addScore(LinkedList<ScoreUnit> scoresList, ScoreUnit scoreUnit) {

        if (scoresList.isEmpty() || scoresList.get(0).getName() == null) {
            scoresList.add(scoreUnit);
            return scoreUnit;
        }

        int i = 0;

        while (i < scoresList.size() && scoreUnit.getPoints() > scoresList.get(i).getPoints()) {
            i++;
        }

        if (i == 0) {
            if (scoresList.size() < 10) {
                scoresList.addToHead(scoreUnit);
                return scoreUnit;
            }
        } else {
            scoresList.addAtIndex(i, scoreUnit);
            if (scoresList.size() > 10)
                scoresList.removeHead();
            return scoreUnit;
        }
        return null;
    }
}
