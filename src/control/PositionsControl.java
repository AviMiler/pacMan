package control;

import model.Map;
import model.elements.*;
import services.Consts;
import services.DB.Arrays;
import services.Services;
import view.GamePanel;
import view.Screen;

import java.awt.*;

public class PositionsControl {

    public static void updatePacMan(PacMan pacMan, GamePanel gamePanel) {

        pacMan.beat();

        switch (gamePanel.getDirection()) {
            case Consts.UP:
                pacMan.setDirection(Consts.UP);
                if (!Collisions.isTouchWall(pacMan, Consts.UP)) {
                    pacMan.setPixelPosition(Consts.UP);
                }
                break;
            case Consts.DOWN:
                pacMan.setDirection(Consts.DOWN);
                if (!Collisions.isTouchWall(pacMan, Consts.DOWN)) {
                    pacMan.setPixelPosition(Consts.DOWN);
                }
                break;
            case Consts.LEFT:
                pacMan.setDirection(Consts.LEFT);
                if (pacMan.getPixelPoint().x - pacMan.getSpeed() <= 0)
                    pacMan.setPixelPosition(Consts.END);
                else if (!Collisions.isTouchWall(pacMan, Consts.LEFT)) {
                    pacMan.setPixelPosition(Consts.LEFT);
                }
                break;
            case Consts.RIGHT:
                pacMan.setDirection(Consts.RIGHT);
                if (pacMan.getPixelPoint().x + pacMan.getSpeed() >= Map.getPixelsWidth() - 32)
                    pacMan.setPixelPosition(Consts.START);
                else if (!Collisions.isTouchWall(pacMan, Consts.RIGHT)) {
                    pacMan.setPixelPosition(Consts.RIGHT);
                }
                break;
        }
        pacMan.setImage(pacMan.getDirection());
    }

    public static void updatePrise(Element element,Arrays<Ghost> ghosts) {

        Position position = Map.getPositionAt(element.getIndexPositionY(), element.getIndexPositionX());

        if (position.isPrise()) {//pacMan touch
            element.updateScore(position.getPrise().getScore());
            if (position.getPrise().getType()==1){
                for (int i = 0; i < ghosts.size(); i++) {
                        ghosts.get(i).setToFrightenMode();
                    }
                GameLoop.startFrightenTimer();
            }
            if (position.getPrise().getType() > 1) {
                GameLoop.startPriseTimeToPut();
            }
            if (position.getPrise().getType() < 2 || position.wasCoin())
                GameLoop.removeFromPriseCnt();
            position.deletePrise();
        }
        if (GameLoop.getPriseTimeToPut() == 0) {
            if (!Map.getPrisePosition().isSpacialPrise()) {
                Map.putPrise();
                GameLoop.startPriseTimeToEnd();
            } else
                GameLoop.startPriseTimeToPut();
        }
        if (GameLoop.getPriseTimeToEnd() == 0) {
            Map.deletePrise();
            GameLoop.startPriseTimeToPut();
        }
    }

    public static void updateGhosts(Arrays<Ghost> ghosts, PacMan pacMan) {

        for (int i = 0; i < ghosts.size(); i++) {
            Ghost ghost = ghosts.get(i);
            ghost.beat();
            ghost.setModeFromGameLoop(GameLoop.getGhostMode());
            ghost.targetCalculator(pacMan,ghosts.get(0));
            ghost.calculateDirection(ghost.getTarget());
            ghost.setPixelPosition(ghost.getDirection());
            ghost.strait();
        }
    }

    public static void updateConflict(PacMan pacMan,Arrays<Ghost> ghosts) {
        for (int i = 0; i < ghosts.size(); i++) {
            if(Collisions.isTouching(pacMan, ghosts.get(i))) {
                if (ghosts.get(0).isEatable()){

                }

            }
        }
    }
}
