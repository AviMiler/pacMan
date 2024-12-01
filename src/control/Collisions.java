package control;

import model.Map;
import model.elements.Element;
import services.Consts;
import view.Screen;

public class Collisions {

    public static boolean isIndexTouchWall(int x, int y) {
        if (x==-1 || x >= Map.getMap().size())
            return false;
        return !Map.getMap().get(y).get(x).isPath();
    }

    public static boolean isIndexTouchWall(Element element, int dir) {
        return switch (dir) {
            case Consts.UP -> isIndexTouchWall(element.getIndexPositionX(), element.getIndexPositionY() - 1);

            case Consts.DOWN -> isIndexTouchWall(element.getIndexPositionX(), element.getIndexPositionY() + 1);

            case Consts.LEFT -> isIndexTouchWall(element.getIndexPositionX() - 1, element.getIndexPositionY());

            case Consts.RIGHT -> isIndexTouchWall(element.getIndexPositionX() + 1, element.getIndexPositionY());

            default -> false;
        };
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

        int w = Screen.getTileSize();
        int x1 = a.getPixelPositionX();
        int y1 = a.getPixelPositionY();
        int x2 = b.getPixelPositionX();
        int y2 = b.getPixelPositionY();
        boolean xOverlap = x1 < x2 + w && x1 + w > x2;
        boolean yOverlap = y1 < y2 + w && y1 + w > y2;

        return xOverlap && yOverlap;
    }
}
