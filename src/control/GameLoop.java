package control;

import model.elements.Ghost;
import model.elements.PacMan;
import model.elements.Position;
import model.elements.Prise;
import services.*;
import services.DB.Arrays;
import view.*;
import model.*;

public class GameLoop{

    private PacMan pacMan;
    private Arrays<Ghost> ghosts;
    private static int priseTimeToPut,priseTimeToEnd;
    private static int numOfPrise;
    private static int level;
    private static int status = 0;
    private static int statusCnt = 0;

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

        for (level = 1; level < 4;level++) {

            pacMan.setPosition();
            ghosts = Ghost.initializeGhostList();
            Map.putPrise();
            startPriseTimeToEnd();

            while (gamePanel.getGameThread() != null && !endLevel) {

                currentTime = System.nanoTime();
                drawDelta += (currentTime - lastTime) / drawInterval;
                lastTime = currentTime;

                if (numOfPrise<=0)
                    endLevel = true;

                if (drawDelta >= 1) {

                    drawDelta--;
                    beatStatus();
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
        gamePanel.endGame();
    }

    private void update(GamePanel gamePanel) {

        updateTimes();
        PositionsControl.updatePrise(pacMan);
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

    private void updateTimes() {

        if (priseTimeToEnd > 0)
            priseTimeToEnd--;
        if (priseTimeToPut > 0)
            priseTimeToPut--;

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

    public static int getStatus(){
        return status;
    }

    public void beatStatus(){
        if (statusCnt==0){
            statusCnt=1000/ GameLoop.getLevel();
            if (status==1)
                status=0;
            else
                status=1;
        }
        statusCnt--;
    }

}
