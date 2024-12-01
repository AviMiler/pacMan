package control;

import model.elements.Ghost;
import model.elements.PacMan;
import services.Consts;
import data.ineerDB.Arrays;
import services.Timer;
import view.*;
import model.*;

public class GameLoop {

    private static PacMan pacMan;
    private static Arrays<Ghost> ghosts;
    private static int priseTimeToPut, priseTimeToEnd;
    private static int chaseTimer, scatterTimer, frightenTimer;
    private static int gameTime;
    private static int numOfPrise;
    private static int level;
    private static GamePanel panel;
    private static int numOfGhostsReleased;
    private static int numOfGhostsToReleased;

    public static void startGame() {

        gameTime = 0;
        panel = (GamePanel) Window.getPanel();
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        panel.requestFocus();
        pacMan = new PacMan();

        ///////////////////////the game loop//////////////////////////

        for (level = 1; level < 4 && pacMan.getLife() > 0; level++) {

            pacMan.setPosition();
            pacMan.setImage(0);
            ghosts = Ghost.initializeGhostList();
            Map.putPrise();
            startPriseTimeToEnd();
            startChaseTimer();
            numOfGhostsReleased = 1;
            numOfGhostsToReleased = 0;

            theGame();

            if (pacMan.getLife() > 0)
                betweenLevels();
        }
        EndGamePanel endGamePanel = new EndGamePanel(0);
        endGamePanel.endGame(pacMan);
        Window.setPanel(endGamePanel);
        endGamePanel.repaint();
    }

    private static void theGame() {

        boolean start = true;

        while (pacMan.getLife() > 0) {
            if (numOfPrise < 0)
                return;

            freeGhostsManager();
            Timer.waitFor();

            panel.print(pacMan, ghosts);

            if (start) {
                start = false;
                Timer.waitFor(3);
            }

            update();
        }

    }

    private static void betweenLevels() {

        numOfPrise = 0;
        Map.updateMap();
        pacMan.addLife();
        panel.endLevel();
        while (panel.getDirection() != Consts.SPACE) {
            panel.repaint();
        }

    }

    private static void update() {

        updateTimes();
        PositionsControl.updatePrise(pacMan, ghosts);
        PositionsControl.updatePacMan(pacMan);
        PositionsControl.updateGhosts(ghosts, pacMan);
        PositionsControl.updateConflict(pacMan, ghosts);

    }

    public static void setPacmanEaten() {

        if (pacMan.getLife() > 0) {
            pacMan.setPosition();
            pacMan.setImage(Consts.EATEN);
            ghosts = Ghost.initializeGhostList();
            for (int i = 0; i < 3; i++) {//simulation
                pacMan.changeImage();
                panel.print(pacMan, ghosts);
                Timer.waitFor(1);
            }
            pacMan.setImage(0);
            numOfGhostsToReleased = 0;
            panel.setDirection();
        }
    }

    private static void freeGhostsManager() {

        int seconds;
        if (numOfGhostsToReleased < numOfGhostsReleased - 1) {
            seconds = 3;
        } else {
            seconds = 10;
        }
        if ((gameTime % (60 * seconds)) == 0 && numOfGhostsToReleased < 4) {
            ghosts.get(numOfGhostsToReleased).release();
            numOfGhostsToReleased++;
            if (seconds == 10)
                numOfGhostsReleased++;
        }
    }

    //////////////timers and counters//////////////

    public static void startPriseTimeToPut() {
        priseTimeToPut = 200;
        priseTimeToEnd = -1;
    }

    public static int getPriseTimeToPut() {
        return priseTimeToPut;
    }

    public static void startPriseTimeToEnd() {
        priseTimeToEnd = 5000 / level / Map.getPrisePosition().getPrise().getType();
        priseTimeToPut = -1;
    }

    public static int getPriseTimeToEnd() {
        return priseTimeToEnd;
    }

    public static void startFrightenTimer() {
        frightenTimer = 500 / level;
        scatterTimer = -1;
        chaseTimer = -1;
    }

    public static void startChaseTimer() {
        chaseTimer = 500 * level;
        frightenTimer = -1;
        scatterTimer = -1;
    }

    public static void startScatterTimer() {
        scatterTimer = 500;
        chaseTimer = -1;
        frightenTimer = -1;
    }

    public static int getGhostMode() {
        if (scatterTimer >= 0) {
            return Consts.SCATTER;
        } else if (chaseTimer >= 0) {
            return Consts.CHASE;
        } else if (frightenTimer >= 0)
            return Consts.FRIGHTENED;
        return 0;
    }

    private static void updateTimes() {

        gameTime++;
        if (priseTimeToEnd > 0)
            priseTimeToEnd--;
        if (priseTimeToPut > 0)
            priseTimeToPut--;
        if (frightenTimer > 0)
            frightenTimer--;
        if (scatterTimer > 0)
            scatterTimer--;
        if (chaseTimer > 0)
            chaseTimer--;
        if (frightenTimer == 0) {
            pacMan.resetGhostEatenCnt();
            startChaseTimer();
            PositionsControl.updateGhostsMode(ghosts, Consts.CHASE, 0);
        }
        if (chaseTimer == 0) {
            startScatterTimer();
            PositionsControl.updateGhostsMode(ghosts, Consts.SCATTER, 0);
        }
        if (scatterTimer == 0) {
            startChaseTimer();
            PositionsControl.updateGhostsMode(ghosts, Consts.CHASE, 0);
        }

    }

    public static int getLevel() {
        return level;
    }

    public static int getGameTime() {
        return gameTime;
    }

    public static void addToPriseCnt() {
        numOfPrise++;
    }

    public static void removeFromPriseCnt() {
        numOfPrise--;
    }
}
