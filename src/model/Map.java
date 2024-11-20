package model;

import control.GameLoop;
import model.elements.Position;
import services.DB.Arrays;
import services.Services;
import view.Screen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Map {

    private static Arrays<Arrays<Position>> map;
    private static Arrays<Position> listOfPositions;
    private static final String mapElementsPath = "C:\\Users\\User\\OneDrive\\מסמכים\\לימודים\\java\\IdeaProjects\\PacMan\\res\\mapElements\\1.png";
    private static final String mapPath = "C:\\Users\\User\\OneDrive\\מסמכים\\לימודים\\java\\IdeaProjects\\PacMan\\res\\maps\\map";
    private static Position prisePosition;

    public static void updateMap(){

        map = new Arrays<>();
        listOfPositions = new Arrays<>();
        analyzeMap(readMap());

    }

    public static Arrays<Arrays<Position>> getMap(){
        return map;
    }

    public static void putPrise(){
        prisePosition = listOfPositions.get(Services.getRandomInt(0,listOfPositions.size()-1));
        prisePosition.setPrise(-1);
    }

    public static void deletePrise(){
        if (prisePosition != null) {
            if (!prisePosition.wasCoin())
                prisePosition.deletePrise();
            else
                prisePosition.setPrise(0);
        }
    }

    public static Position getPrisePosition(){
        return prisePosition;
    }

    public static int getTilesWidth(){
        return map.get(0).size();
    }
    public static int getTilesHeight(){
        return map.size();
    }

    public static int getIndexPMStartX(){
        return map.get(0).size()/2;
    }
    public static int getIndexPMStartY(){
        return map.size()-2;
    }

    public static int getGhostsStartX(){
        return 10*Screen.getTileSize();
    }
    public static int getGhostsStartY(){
        return 11*Screen.getTileSize();
    }

    public static int getPriseX(){
        return map.get(0).size()/2;
    }
    public static int getPriseY(){
        return map.size()/2;
    }

    public static int getPixelsWidth(){
        return map.get(0).size()* Screen.getTileSize();
    }
    public static int getPixelsHeight(){
        return map.size()*Screen.getTileSize();
    }

    public static String getMapElementPath(){
        return mapElementsPath;
    }

    public static String getMapPath(){
        return mapPath;
    }

    public static Position getPositionAt(int y, int x){
        return map.get(y).get(x);
    }

    public static Position getStartPrisePosition(){
        return map.get(getPriseY()).get(getPriseX());
    }

    private static Arrays<Arrays<Integer>> readMap(){

        Arrays<Arrays<Integer>> map = new Arrays<>();
        try {

            BufferedReader br = new BufferedReader(new FileReader(mapPath));
            String s;
            while ((s = br.readLine())!=null) {

                map.add(convertStringsToInts(s));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return map;

    }

    private static void analyzeMap(Arrays<Arrays<Integer>> map0) {

        Position position;
        for (int i = 0; i < map0.size(); i++) {
            map.add(new Arrays<>());
            for (int j = 0; j < map0.get(i).size(); j++) {
                if (map0.get(i).get(j) == 0) {
                    position = new Position(true, 0);
                } else if (map0.get(i).get(j) == 1) {
                    position = new Position(false, 0);
                    GameLoop.addToPriseCnt();
                }else if (map0.get(i).get(j) == 3) {
                    position = new Position(false, 1);
                    GameLoop.addToPriseCnt();
                }
                else {
                    position = new Position(false, -2);
                }
                map.get(i).add(position);
                if (!position.isWall() && position.isPrise()){//enter to position that can put a prise
                    listOfPositions.add(position);
                }
            }
        }
    }

    private static Arrays<Integer> convertStringsToInts(String s){
        String[] arr = s.split(" ");
        Arrays<Integer> ints = new Arrays<>();
        for (int i = 0; i < arr.length; i++) {
            ints.add(Integer.parseInt(arr[i]));
        }
        return ints;
    }
}
