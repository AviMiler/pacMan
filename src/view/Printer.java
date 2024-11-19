package view;

import control.GameLoop;
import model.Map;
import model.elements.Ghost;
import model.elements.PacMan;
import model.elements.Prise;
import services.Consts;
import services.DB.Arrays;

import javax.swing.*;
import java.awt.*;

public class Printer extends JPanel {

    static int m = Screen.getTileSize()*2;

    public static void printMap(Graphics2D g) {

        Image image;
        int screenX=0, screenY=0;

        for (int i = 0; i < Map.getMap().size(); i++) {
            for (int j = 0;j < Map.getMap().get(i).size(); j++) {

                if (Map.getMap().get(i).get(j).isWall()) {
                    image = new ImageIcon(Map.getMapElementPath()).getImage();
                    g.drawImage(image, screenX, screenY + m, Screen.getTileSize(), Screen.getTileSize(), null);
                }
                else if (Map.getMap().get(i).get(j).isPrise()){
                    image = new ImageIcon(Map.getMap().get(i).get(j).getPrise().getImagesPath()).getImage();
                    g.drawImage(image, screenX, screenY + m, Screen.getTileSize(), Screen.getTileSize(), null);
                }
                screenX += Screen.getTileSize();
            }
            screenY += Screen.getTileSize();screenX=0;
        }

    }

    public static void printPacMan(PacMan pacMan, Graphics2D g) {

        g.drawImage(pacMan.getImage(),pacMan.getPixelPoint().x, pacMan.getPixelPoint().y + m, Screen.getTileSize(), Screen.getTileSize(), null);

    }

    public static void printGhosts(Arrays<Ghost> ghosts,Graphics2D g) {
        Arrays<Color> c = new Arrays<>();
        c.add(Color.RED);c.add(Color.CYAN);c.add(Color.PINK);c.add(Color.ORANGE);
        for (int i = 0; i < ghosts.size(); i++) {
            g.setColor(c.get(i));
            g.setFont(g.getFont().deriveFont(24f));
            g.drawString("X",ghosts.get(i).getTarget().x,ghosts.get(i).getTarget().y + m);
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
        Image image = new ImageIcon("C:\\Users\\User\\OneDrive\\מסמכים\\לימודים\\java\\IdeaProjects\\PacMan\\res\\info\\life.png").getImage();
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
        int time = GameLoop.getPriseTimeToEnd();
        if (time!=-1){
            g.setFont(Screen.customFont);
            g.setColor(Color.WHITE);
            g.drawString("Prise time:" + time/60, 0, 52);
        }

    }

    public static void printEndLevel(Graphics2D g){
        g.setFont(Screen.customFont);
        g.setColor(Color.WHITE);
        g.drawString("End Level", 9*Screen.getTileSize(), 10*Screen.getTileSize());
        g.drawString("press space", 8*Screen.getTileSize(), 11*Screen.getTileSize());

    }

    public static void printEndGame(Graphics2D g){
        g.setFont(Screen.customFont);
        g.setColor(Color.RED);
        g.drawString("END GAME", 9*Screen.getTileSize(), 10*Screen.getTileSize());
    }
}
