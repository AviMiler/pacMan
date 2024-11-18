package view;

import model.elements.Ghost;
import model.elements.PacMan;
import model.elements.Prise;
import services.*;
import control.*;
import model.*;
import services.DB.Arrays;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    Thread gameThread;
    private static PacMan pacMan = new PacMan();
    private static Arrays<Ghost> ghosts = new Arrays<>();
    private static boolean endLevel = false;
    private static boolean endGame = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(Screen.getScreenWidth(),Screen.getScreenHeight()));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.startGameThread();
        this.requestFocusInWindow();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        strait();
        pacMan.beat();
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
        else if (endGame){
            Printer.printEndGame(graphics2D);
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

    public static void setData(PacMan pacMan,Arrays<Ghost> ghosts) {
        GamePanel.pacMan = pacMan;
        GamePanel.ghosts = ghosts;
    }

    public void endLevel(){
        endLevel = true;
        repaint();
        while (endLevel)
            System.out.println("wait");
    }

    public void endGame(){
        endGame = true;
        repaint();
    }


    ////////////////////////////////////////
    public boolean up, down, left, right;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W:
                if (!Collisions.isTouchWall(pacMan.getIndexPositionX(), pacMan.getIndexPositionY() - 1)) {
                    down = left = right = false;
                    up = true;
                }
                break;
            case KeyEvent.VK_S:
                if (!Collisions.isTouchWall(pacMan.getIndexPositionX(), pacMan.getIndexPositionY() + 1)) {
                    up = left = right = false;
                    down = true;
                }
                break;
            case KeyEvent.VK_A:
                if (!Collisions.isTouchWall(pacMan.getIndexPositionX() - 1, pacMan.getIndexPositionY())) {
                    up = down = right = false;
                    left = true;
                }
                break;
            case KeyEvent.VK_D:
                if (!Collisions.isTouchWall(pacMan.getIndexPositionX() + 1, pacMan.getIndexPositionY())) {
                    up = down = left = false;
                    right = true;
                }
                break;
            case KeyEvent.VK_SPACE:
                    endLevel = false;
                    up=down=left=right=false;
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
        return 0;
    }
}
