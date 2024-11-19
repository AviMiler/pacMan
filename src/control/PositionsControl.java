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
                pacMan.setImage(Consts.UP);
                if (!Collisions.isTouchWall(pacMan, Consts.UP)) {
                    pacMan.setPixelPosition(Consts.UP);
                }
                break;
            case Consts.DOWN:
                pacMan.setImage(Consts.DOWN);
                if (!Collisions.isTouchWall(pacMan, Consts.DOWN)) {
                    pacMan.setPixelPosition(Consts.DOWN);
                }
                break;
            case Consts.LEFT:
                pacMan.setImage(Consts.LEFT);
                if (pacMan.getPixelPoint().x - pacMan.getSpeed() <= 0)
                    pacMan.setPixelPosition(Consts.END);
                else if (!Collisions.isTouchWall(pacMan, Consts.LEFT)) {
                    pacMan.setPixelPosition(Consts.LEFT);
                }
                break;
            case Consts.RIGHT:
                pacMan.setImage(Consts.RIGHT);
                if (pacMan.getPixelPoint().x + pacMan.getSpeed() >= Map.getPixelsWidth() - 32)
                    pacMan.setPixelPosition(Consts.START);
                else if (!Collisions.isTouchWall(pacMan, Consts.RIGHT)) {
                    pacMan.setPixelPosition(Consts.RIGHT);
                }
                break;

        }
    }

    public static void updatePrise(Element element) {

        Position position = Map.getPositionAt(element.getIndexPositionY(), element.getIndexPositionX());

        if (position.isPrise()) {//pacMan touch
            element.updateScore(position.getPrise().getScore());
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
            if (i == 0) {
                ghost.calculateDirection(new Point(pacMan.getIndexPositionX()*Screen.getTileSize(), pacMan.getIndexPositionY()*Screen.getTileSize()));
            }
            switch (ghost.getDirection()) {
                case Consts.UP:
                    ghost.setPixelPosition(Consts.UP);
                    ghost.setImage(Consts.UP);
                    break;
                case Consts.DOWN:
                    ghost.setPixelPosition(Consts.DOWN);
                    ghost.setImage(Consts.DOWN);
                    break;
                case Consts.LEFT:
                    ghost.setPixelPosition(Consts.LEFT);
                    ghost.setImage(Consts.LEFT);
                    break;
                case Consts.RIGHT:
                    ghost.setPixelPosition(Consts.RIGHT);
                    ghost.setImage(Consts.RIGHT);
                    break;
            }
            ghost.strait();
        }
    }
}
