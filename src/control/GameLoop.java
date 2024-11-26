package control;

import data.DB.DataBaseHandler;
import data.DB.ScoreUnit;
import data.HandleScores;
import data.ineerDB.LinkedList;
import model.elements.Ghost;
import model.elements.PacMan;
import services.Consts;
import data.ineerDB.Arrays;
import services.Timer;
import view.*;
import model.*;

import javax.swing.*;

public class GameLoop{

    private static PacMan pacMan;
    private static Arrays<Ghost> ghosts;
    private static int priseTimeToPut,priseTimeToEnd;
    private static int chaseTimer,scatterTimer,frightenTimer;
    private static int gameTime;
    private static int numOfPrise;
    private static int level;
    private static Panel panel;
    private static int numOfGhostsReleased = 1;
    private static int numOfGhostsToReleased = 0;

    public void startGame() {

        LinkedList<ScoreUnit> scoresList = DataBaseHandler.readScoresFromFile();

        gameTime=0;
        Map.updateMap();
        Screen.updateScreen();
        panel=Window.startWindow();
        if (panel==null) return;
        panel.setFocusable(true);
        panel.requestFocus();

        pacMan = new PacMan();

        ///////////////////////the game loop//////////////////////////

        for (level = 1; level < 4 && pacMan.getLife() > 0;level++) {

            pacMan.setPosition();
            pacMan.setImage(0);
            ghosts = Ghost.initializeGhostList();
            Map.putPrise();
            startPriseTimeToEnd();
            startChaseTimer();

            theGame();

            if (pacMan.getLife()>0)
                betweenLevels();
        }

        panel = Window.replacePanel(panel,new EndGamePanel());
        ((EndGamePanel)panel).endGame(scoresList,pacMan);
        panel.repaint();

    }

    private void theGame() {

        boolean start = true;

        while (((GamePanel) panel).getGameThread() != null && pacMan.getLife() > 0) {

            if (numOfPrise <= 0)
                return;

            freeGhostsManager();
            Timer.waitFor();

            ((GamePanel) panel).print(pacMan, ghosts);

            if (start) {start=false;Timer.waitFor(3);}

            update((GamePanel) panel);
        }
    }

    private void betweenLevels() {

        numOfPrise = 0;
        Map.updateMap();
        pacMan.addLife();
        ((GamePanel) panel).endLevel();
        while (((GamePanel) panel).getDirection() != Consts.SPACE) {
            panel.repaint();
        }

    }

    private void update(GamePanel gamePanel) {

        updateTimes();
        PositionsControl.updatePrise(pacMan,ghosts);
        PositionsControl.updatePacMan(pacMan, gamePanel);
        PositionsControl.updateGhosts(ghosts,pacMan);
        PositionsControl.updateConflict(pacMan,ghosts);

    }

    public static void setPacmanEaten(){

        if (pacMan.getLife()>0) {
            pacMan.setPosition();
            pacMan.setImage(Consts.EATEN);
            ghosts = Ghost.initializeGhostList();
            for (int i = 0; i < 3; i++) {//simulation
                pacMan.changeImage();
                ((GamePanel)panel).print(pacMan, ghosts);
                Timer.waitFor(1);
            }
            pacMan.setImage(0);
            numOfGhostsToReleased=0;
        }
    }

    private static void freeGhostsManager() {

        int seconds;
        if (numOfGhostsToReleased < numOfGhostsReleased-1) {
            seconds = 3;
        }
        else {
            seconds = 10;
        }
        if ((gameTime%(60*seconds))==0 && numOfGhostsToReleased<4) {
            ghosts.get(numOfGhostsToReleased).release();
            numOfGhostsToReleased++;
            if (seconds==10)
                numOfGhostsReleased++;
        }
    }

    //////////////timers and counters//////////////

    public static void startPriseTimeToPut() {
        priseTimeToPut=200;
        priseTimeToEnd=-1;
    }

    public static int getPriseTimeToPut(){
        return priseTimeToPut;
    }

    public static void startPriseTimeToEnd() {
        priseTimeToEnd = 5000/level/Map.getPrisePosition().getPrise().getType();
        priseTimeToPut = -1;
    }

    public static int getPriseTimeToEnd() {
        return priseTimeToEnd;
    }

    public static void startFrightenTimer() {
        frightenTimer = 500/level;
        scatterTimer=-1;
        chaseTimer=-1;
    }

    public static void startChaseTimer() {
        chaseTimer=500 * level;
        frightenTimer = -1;
        scatterTimer=-1;
    }

    public static void startScatterTimer() {
        scatterTimer=500;
        chaseTimer=-1;
        frightenTimer=-1;
    }

    public static int getGhostMode(){
        if (scatterTimer>=0) {
            return Consts.SCATTER;
        }
        else if (chaseTimer>=0) {
            return Consts.CHASE;
        }
        else if (frightenTimer>=0)
            return Consts.FRIGHTENED;
        return 0;
    }

    private void updateTimes() {

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
        if (frightenTimer == 0){
            pacMan.resetGhostEatenCnt();
            startChaseTimer();
            PositionsControl.updateGhostsMode(ghosts,Consts.CHASE,0);
        }
        if (chaseTimer == 0){
            startScatterTimer();
            PositionsControl.updateGhostsMode(ghosts,Consts.SCATTER,0);
        }
        if (scatterTimer == 0){
            startChaseTimer();
            PositionsControl.updateGhostsMode(ghosts,Consts.CHASE,0);
        }

    }

    public static int getLevel(){
        return level;
    }

    public static int getGameTime(){
        return gameTime;
    }

    public static void addToPriseCnt(){
        numOfPrise++;
    }

    public static void removeFromPriseCnt(){
        numOfPrise--;
    }
}
