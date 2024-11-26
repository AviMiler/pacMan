package view;

import data.DB.DataBaseHandler;
import data.DB.ScoreUnit;
import data.ineerDB.LinkedList;
import model.elements.Ghost;
import model.elements.PacMan;
import services.*;
import control.*;
import data.ineerDB.Arrays;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends Panel implements Runnable, KeyListener {

    Thread gameThread;
    private PacMan pacMan = new PacMan();
    private Arrays<Ghost> ghosts = new Arrays<>();
    private static boolean endLevel = false;
    private static LinkedList<ScoreUnit> scoresList;

    public GamePanel() {
        super();
        this.startGameThread();
        this.addKeyListener(this);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        strait();
        repaint();

    }

    private void strait() {
        if (getDirection()==Consts.UP || getDirection()==Consts.DOWN) {
            pacMan.straitX();
        }
        if (getDirection()==Consts.LEFT || getDirection()==Consts.RIGHT) {
            pacMan.straitY();
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        if (endLevel){
            Printer.printEndLevel(graphics2D);
        }
        else {
            Printer.printMap(graphics2D);
            Printer.printPacMan(pacMan, graphics2D);
            Printer.printGhosts(ghosts, graphics2D);
            Printer.printData(pacMan, graphics2D);
        }

    }

    public Thread getGameThread() {
        return gameThread;
    }

    public void print(PacMan pacMan, Arrays<Ghost> ghosts) {
        this.pacMan = pacMan;
        this.ghosts = ghosts;
        this.run();
    }
    public void endLevel() {
        endLevel = !endLevel;
    }

    ////////////////////////////////////////Key Handler///////////////////////////////////
    public boolean up, down, left, right,space;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (!Collisions.isIndexTouchWall(pacMan.getIndexPositionX(), pacMan.getIndexPositionY() - 1)) {
                    down = left = right = space =false;
                    up = true;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (!Collisions.isIndexTouchWall(pacMan.getIndexPositionX(), pacMan.getIndexPositionY() + 1)) {
                    up = left = right = space =false;
                    down = true;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (!Collisions.isIndexTouchWall(pacMan.getIndexPositionX() - 1, pacMan.getIndexPositionY())) {
                    up = down = right = space =false;
                    left = true;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!Collisions.isIndexTouchWall(pacMan.getIndexPositionX() + 1, pacMan.getIndexPositionY())) {
                    up = down = left = space = false;
                    right = true;
                }
                break;
            case KeyEvent.VK_SPACE:
                    endLevel = false;
                    up=down=left=right=false;
                    space=true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public int getDirection() {
        if (up){
            return Consts.UP;
        }
        else if (down){
            return Consts.DOWN;
        }
        else if (left){
            return Consts.LEFT;
        }
        else if (right){
            return Consts.RIGHT;
        }
        else if (space){
            return Consts.SPACE;
        }
        return 0;
    }

    public void setDirection(){
        up=down=left=right=space=false;
    }

}
