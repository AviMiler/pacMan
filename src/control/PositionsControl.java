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

    public static void updatePacMan(PacMan pacMan,GamePanel gamePanel) {

        int newIndex;
        pacMan.beat();

        if (gamePanel.getDirection() == Consts.UP) {
            newIndex = pacMan.getPixelPositionY() - pacMan.getSpeed();
            if (!Collisions.isTouchWall(pacMan.getIndexPositionX(),(newIndex)/Screen.getTileSize())){
                pacMan.setImage(Consts.UP);
                pacMan.setPixelPositionY(newIndex);
            }
        }
        else if (gamePanel.getDirection()==Consts.DOWN) {
            newIndex = pacMan.getPixelPositionY() + pacMan.getSpeed();
            if (!Collisions.isTouchWall(pacMan.getIndexPositionX(),(newIndex)/Screen.getTileSize()+1)){
                pacMan.setImage(Consts.DOWN);
                pacMan.setPixelPositionY(newIndex);
            }
        }
        else if (gamePanel.getDirection()==Consts.LEFT) {
            newIndex = pacMan.getPixelPositionX() - pacMan.getSpeed();
            if (newIndex <= 0)
                pacMan.setPixelPositionX(Map.getTilesWidth() * Screen.getTileSize()-17);
            else if (!Collisions.isTouchWall((newIndex)/Screen.getTileSize(),pacMan.getIndexPositionY())){
                pacMan.setImage(Consts.LEFT);
                pacMan.setPixelPositionX(newIndex);
            }
        }
        else if (gamePanel.getDirection()==Consts.RIGHT) {
            newIndex = pacMan.getPixelPositionX() + pacMan.getSpeed();
            if (newIndex >= Map.getPixelsWidth()-32)
                pacMan.setPixelPositionX(0);
            else if (!Collisions.isTouchWall((newIndex)/Screen.getTileSize()+1,pacMan.getIndexPositionY())){
                pacMan.setImage(Consts.RIGHT);
                pacMan.setPixelPositionX(newIndex);
            }
        }
    }

    public static void updatePrise(PacMan pacMan) {

        Position position = Map.getPositionAt(pacMan.getIndexPositionY(),pacMan.getIndexPositionX());

        if (position.isPrise()) {//pacMan touch
            pacMan.updateScore(position.getPrise().getScore());
            if (position.getPrise().getType() > 1) {
                GameLoop.startPriseTimeToPut();
            }
            if (position.getPrise().getType() < 2 || position.wasCoin())
                GameLoop.removeFromPriseCnt();
            position.deletePrise();
        }
        if (GameLoop.getPriseTimeToPut()==0){
            if (!Map.getPrisePosition().isSpacialPrise()) {
                Map.putPrise();
                GameLoop.startPriseTimeToEnd();
            }
            else
                GameLoop.startPriseTimeToPut();
        }
        if (GameLoop.getPriseTimeToEnd()==0) {
            Map.deletePrise();
            GameLoop.startPriseTimeToPut();
        }
    }

    public static void updateGhosts(Arrays<Ghost> ghosts,PacMan pacMan) {

        for (int i = 0; i < ghosts.size(); i++) {
            ghosts.get(i).beat();
            if (i == 0) {
                Ghost ghost = ghosts.get(i);
                ghost.calculateDirection(new Point(pacMan.getPixelPositionX(), pacMan.getPixelPositionY()));
                switch (ghost.getDirection()) {
                    case Consts.UP:
                        ghosts.get(i).setPixelPositionY(ghosts.get(i).getPixelPositionY() - ghost.getSpeed());
                        ghost.straitX();
                        ghost.setImage(Consts.UP);
                        break;
                    case Consts.DOWN:
                        ghosts.get(i).setPixelPositionY(ghosts.get(i).getPixelPositionY() + ghost.getSpeed());
                        ghost.straitX();
                        ghost.setImage(Consts.DOWN);
                        break;
                    case Consts.LEFT:
                        ghosts.get(i).setPixelPositionX(ghosts.get(i).getPixelPositionX() - ghost.getSpeed());
                        ghost.straitY();
                        ghost.setImage(Consts.LEFT);
                        break;
                    case Consts.RIGHT:
                        ghosts.get(i).setPixelPositionX(ghosts.get(i).getPixelPositionX() + ghost.getSpeed());
                        ghost.straitY();
                        ghost.setImage(Consts.RIGHT);
                        break;
                }
            }
        }
    }
}
