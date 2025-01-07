package data.DB;

import data.ineerDB.LinkedList;
import services.Consts;
import services.Services;

import java.io.*;

public class DataBaseHandler {

   private static final String scorePath = Consts.ELEMENT_PATH+"data\\highScores\\highScores";
//    private static final String scorePath = "resources/data/highScores/highScores";

    public static LinkedList<ScoreUnit> readScoresFromFile() {
        LinkedList<ScoreUnit> list = new LinkedList<>();

        try (InputStream inputStream = DataBaseHandler.class.getClassLoader().getResourceAsStream(scorePath);
             BufferedReader br = inputStream != null
                     ? new BufferedReader(new InputStreamReader(inputStream))
                     : new BufferedReader(new FileReader(scorePath))) {

            String line;
            while ((line = br.readLine()) != null) {
                ScoreUnit temp = convertLineToScoreUnit(line);
                list.add(temp);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading scores file", e);
        }

        return list;
    }

//    public static LinkedList<ScoreUnit> readScoresFromFile() {
//        LinkedList<ScoreUnit> list = new LinkedList<>();
//
//        try  {
//            BufferedReader br = Services.getReader(scorePath);
//            String line;
//            while ((line = br.readLine()) != null) {
//                ScoreUnit temp = convertLineToScoreUnit(line);
//                list.add(temp);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Error reading scores file", e);
//        }
//
//        return list;
//    }

    public static void saveScoresListToFile(LinkedList<ScoreUnit> scoresList) {
        File file = new File(scorePath);

        // Resolve external writable path if necessary
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (int i = 0; i < scoresList.size(); i++) {
                bw.write(scoresList.get(i).getName() + "@" + scoresList.get(i).getPoints() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving scores to file: " + e.getMessage(), e);
        }
    }


    private static ScoreUnit convertLineToScoreUnit(String line) {
        String[] parts = line.split("@");
        int points;
        String name;
        try {
            points = Integer.parseInt(parts[1]);
            name = parts[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return new ScoreUnit(name, points);
    }
}
