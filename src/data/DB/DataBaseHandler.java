package data.DB;

import data.ineerDB.Arrays;
import data.ineerDB.LinkedList;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DataBaseHandler {

    private static final String scorePath = "C:\\Users\\User\\OneDrive\\מסמכים\\לימודים\\java\\IdeaProjects\\PacMan\\res\\data\\highScores\\highScors";

    public static LinkedList<ScoreUnit> readScoresFromFile(){

        LinkedList<ScoreUnit> list = new LinkedList<>();
        try {

            BufferedReader br = new BufferedReader(new FileReader(scorePath));
            String line;
            ScoreUnit temp;
            while ((line = br.readLine())!=null) {

                temp = convertLineToScoreUnit(line);
                list.add(temp);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void saveScoresListToFile(LinkedList<ScoreUnit> scoresList) {

        try {
            File file = new File(scorePath);
            file.delete();
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < scoresList.size(); i++) {
                bw.write(scoresList.get(i).getName() + " " + scoresList.get(i).getPoints() + "\n");
            }
            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ScoreUnit convertLineToScoreUnit(String line) {
        String[] parts = line.split(" ");
        int points;
        String name;
        try {
            points = Integer.parseInt(parts[1]);
            name = parts[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return new ScoreUnit(name,points);
    }

}
