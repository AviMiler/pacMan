package control;

import model.elements.Ghost;
import model.elements.PacMan;
import services.Consts;
import services.DB.Arrays;
import view.*;
import model.*;

public class GameLoop{

    private PacMan pacMan;
    private Arrays<Ghost> ghosts;
    private static int priseTimeToPut,priseTimeToEnd;
    private static int chaseTimer,scatterTimer,frightenTimer;
    private static int numOfPrise;
    private static int level;
    private static int status = 0;
    private static int statusCnt = 0;
    private static int gameOverCnt = 3;

    public void startGame() {

        Map.updateMap();
        Screen.updateScreen();
        GamePanel gamePanel = Window.startWindow();
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        final int FPS = 60;
        double drawInterval = (double) 1000000000 / FPS;
        double drawDelta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        boolean endLevel = false;
        pacMan = new PacMan();

        for (level = 1; level < 4 && gameOverCnt> 0;level++) {

            pacMan.setPosition();
            ghosts = Ghost.initializeGhostList();
            Map.putPrise();
            startPriseTimeToEnd();
            startChaseTimer();

            while (gamePanel.getGameThread() != null && !endLevel) {

                currentTime = System.nanoTime();
                drawDelta += (currentTime - lastTime) / drawInterval;
                lastTime = currentTime;

                if (numOfPrise<=0)
                    endLevel = true;

                if (drawDelta >= 1) {

                    drawDelta--;
                    update(gamePanel);
                    GamePanel.setData(pacMan, ghosts);
                    gamePanel.run();

                }
            }
            gamePanel.endLevel();
            numOfPrise=0;
            Map.updateMap();
            pacMan.addLife();
            endLevel=false;

        }
        if (gameOverCnt>0)
            gamePanel.endGame();
    }

    private void update(GamePanel gamePanel) {

        updateTimes();
        PositionsControl.updatePrise(pacMan,ghosts);
        PositionsControl.updatePacMan(pacMan, gamePanel);
        PositionsControl.updateGhosts(ghosts,pacMan);

    }

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
            startChaseTimer();
        }
        if (chaseTimer == 0){
            startScatterTimer();
        }
        if (scatterTimer == 0){
            startChaseTimer();
        }

    }

    public static int getLevel(){
        return level;
    }

    public static void addToPriseCnt(){
        numOfPrise++;
    }
    public static void removeFromPriseCnt(){
        numOfPrise--;
    }
}
