package view;

import control.GameLoop;
import data.DB.ScoreUnit;
import data.ineerDB.LinkedList;
import model.Map;
import model.elements.Ghost;
import model.elements.PacMan;
import data.ineerDB.Arrays;

import javax.swing.*;
import java.awt.*;

public class Printer extends JPanel {

    static int m = Screen.getTileSize()*2;

    public static void printMap(Graphics2D g) {
        Image image;
        int screenX = 0, screenY = 0;

        for (int i = 0; i < Map.getMap().size(); i++) {
            for (int j = 0; j < Map.getMap().get(i).size(); j++) {

                if (Map.getMap().get(i).get(j).isWall()) {
                    image = new ImageIcon(Map.getMapElementPath()+"1.png").getImage();
                } else if (Map.getMap().get(i).get(j).isPrise()) {
                    image = new ImageIcon(Map.getMap().get(i).get(j).getPrise().getImagesPath()).getImage();
                } else if (Map.getMap().get(i).get(j).isGate()) {
                    image = new ImageIcon(Map.getMapElementPath()+"2.png").getImage();
                }else
                    image = new ImageIcon("dd").getImage();



                g.drawImage(image, screenX, screenY + m, Screen.getTileSize(), Screen.getTileSize(), null);
                screenX += Screen.getTileSize();
            }
            screenY += Screen.getTileSize();
            screenX = 0;
        }

    }

    public static void printPacMan(PacMan pacMan, Graphics2D g) {

        g.drawImage(pacMan.getImage(),pacMan.getPixelPoint().x, pacMan.getPixelPoint().y + m, Screen.getTileSize(), Screen.getTileSize(), null);

    }

    public static void printGhosts(Arrays<Ghost> ghosts,Graphics2D g) {
        Arrays<Color> c = new Arrays<>();
        c.add(Color.RED);c.add(Color.PINK);c.add(Color.CYAN);c.add(Color.ORANGE);
        for (int i = 0; i < ghosts.size(); i++) {
            g.setColor(c.get(i));
            g.drawImage(ghosts.get(i).getImage(), ghosts.get(i).getPixelPositionX(), ghosts.get(i).getPixelPositionY() + m, Screen.getTileSize(), Screen.getTileSize(), null);
        }
    }

    public static void printData(PacMan pacMan, Graphics2D g){
        printLife(pacMan,g);
        printScore(pacMan,g);
        printLevel(g);
        printPriseTime(g);
    }

    private static void printLife(PacMan pacMan, Graphics2D g) {
        Image image = new ImageIcon("res\\info\\life.png").getImage();
        for (int i = pacMan.getLife(); i > 0; i--) {
            g.drawImage(image,Screen.getScreenWidth()-i*(Screen.getTileSize()+10), 0,Screen.getTileSize(),Screen.getTileSize(),null);
        }
    }

    private static void printScore(PacMan pacMan, Graphics2D g) {
        g.setFont(Screen.customFont);
        g.setColor(Color.WHITE);
        g.drawString("Score:" + pacMan.getScore(),0 , 25);
    }

    private static void printLevel(Graphics2D g){
        g.setFont(Screen.customFont);
        g.setColor(Color.WHITE);
        g.drawString("Level: " + GameLoop.getLevel(), Screen.getScreenWidth()-7*Screen.getTileSize()+10, 52);
    }

    private static void printPriseTime(Graphics2D g) {
        int priseTime = GameLoop.getPriseTimeToEnd();
        g.setFont(Screen.customFont);
        g.setColor(Color.WHITE);
        g.drawString("Time:" + GameLoop.getGameTime()/60, Screen.getScreenWidth()/2-3*Screen.getTileSize(), 25);
        if (priseTime!=-1){
            g.drawString("Prise time:" + priseTime/60, 0, 52);
        }

    }

    public static void printEndLevel(Graphics2D g){
        g.setFont(Screen.customFont);
        g.setColor(Color.WHITE);
        g.drawString("End Level", 9*Screen.getTileSize(), 10*Screen.getTileSize());
        g.drawString("press space", 8*Screen.getTileSize(), 11*Screen.getTileSize());

    }

    public static void printEndGame(Graphics2D g,boolean end,boolean over){
        g.setFont(Screen.customFont);
        g.setColor(Color.RED);
        if (end)
            g.drawString("END GAME", 9*Screen.getTileSize(), 10*Screen.getTileSize());
        else if (over)
            g.drawString("GAME OVER", 9*Screen.getTileSize(), 10*Screen.getTileSize());
    }

    public static void printScoresList(Graphics2D g,LinkedList<ScoreUnit> scoresList){
        g.setFont(Screen.customFont);
        g.setColor(Color.ORANGE);
        int j = 1;
        g.drawString("BEST SCORES:", 7*Screen.getTileSize(), 150);
        g.setColor(Color.WHITE);
        for (int i = scoresList.size()-1; i >= 0 ; i--) {
            ScoreUnit score = scoresList.get(i);
            if (score.getName().isEmpty())
                score.setName("unknown");
            if (score.getName().length()>8)
                score.setName(score.getName().substring(0, 8));
            g.drawString(score.getName()+dots(score.getName(),score.getPoints())+score.getPoints(), Map.getMap().get(0).size()/4*Screen.getTileSize(), 180+(j)*Screen.getTileSize());
            j++;
        }
    }

    private static String dots(String name,int num){

        int numOfDots = Map.getMap().get(0).size()/2+3 - (name+num).length();
        if (numOfDots<0)
            numOfDots=0;
        return  ".".repeat(numOfDots);

    }

    public static void printGotRecord(Graphics2D g,String name){
        g.setFont(Screen.customFont);
        g.setColor(Color.YELLOW);
        g.drawString("You got a new record:", 4*Screen.getTileSize(), 200);
        g.drawString("please enter your name", 3*Screen.getTileSize(), 200+Screen.getTileSize());
        g.setColor(Color.BLUE);
        g.drawString(name+"_", 5*Screen.getTileSize(), 200+Screen.getTileSize()*3);

    }

    public static void printBye(Graphics2D g){
        g.setColor(Color.YELLOW);
        g.setFont(Screen.customFont);
        g.drawString("BYE BYE )-:", 6*Screen.getTileSize(), 200);
    }
}
