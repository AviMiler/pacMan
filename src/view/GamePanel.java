package view;

import model.elements.Ghost;
import model.elements.PacMan;
import services.*;
import control.*;
import data.ineerDB.Arrays;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends MyPanel implements  KeyListener {

    private PacMan pacMan = new PacMan();
    private Arrays<Ghost> ghosts = new Arrays<>();
    private static boolean endLevel = false;

    public GamePanel() {
        super();
        this.addKeyListener(this);
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

    public void print(PacMan pacMan, Arrays<Ghost> ghosts) {
        this.pacMan = pacMan;
        this.ghosts = ghosts;
        this.repaint();
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
                    pacMan.setDirection(Consts.UP);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (!Collisions.isIndexTouchWall(pacMan.getIndexPositionX(), pacMan.getIndexPositionY() + 1)) {
                    pacMan.setDirection(Consts.DOWN);
                }
                break;
            case KeyEvent.VK_LEFT:
                if (!Collisions.isIndexTouchWall(pacMan.getIndexPositionX() - 1, pacMan.getIndexPositionY())) {
                    pacMan.setDirection(Consts.LEFT);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!Collisions.isIndexTouchWall(pacMan.getIndexPositionX() + 1, pacMan.getIndexPositionY())) {
                    pacMan.setDirection(Consts.RIGHT);
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
        if (space){
            return Consts.SPACE;
        }
        return 0;
    }

    public void setDirection(){
        up=down=left=right=space=false;
    }
}