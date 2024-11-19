package control;

import model.Map;
import model.elements.Element;
import model.elements.PacMan;
import model.elements.Position;
import services.Consts;
import services.DB.Arrays;
import model.elements.Permanent;
import view.Screen;

import java.awt.*;

public class Collisions {

    public static boolean isIndexTouchWall(int x, int y) {
        return Map.getMap().get(y).get(x).isWall();
    }

    public static boolean isTouchWall(Element element, int dir) {
        return switch (dir) {
            case Consts.UP -> isIndexTouchWall(element.getIndexPositionX(),(element.getMiddlePixelY()-Screen.getHalfTileSize())/Screen.getTileSize());

            case Consts.DOWN -> isIndexTouchWall(element.getIndexPositionX(),(element.getMiddlePixelY()+Screen.getHalfTileSize())/Screen.getTileSize());

            case Consts.LEFT -> isIndexTouchWall((element.getMiddlePixelX()-Screen.getHalfTileSize())/Screen.getTileSize(), element.getIndexPositionY());

            case Consts.RIGHT -> isIndexTouchWall((element.getMiddlePixelX()+Screen.getHalfTileSize())/Screen.getTileSize(), element.getIndexPositionY());

            default -> false;
        };
    }

    public static boolean isTouching(Element a,Element b) {
        return a.getIndexPoint().x == b.getIndexPoint().x && a.getIndexPoint().y == b.getIndexPoint().y;
    }
}
