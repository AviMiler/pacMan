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

    public static boolean isTouchWall(int x, int y) {
        return Map.getMap().get(y).get(x).isWall();
    }

    public static boolean isTouchWall(Element element, int dir) {
        return switch (dir) {
            case Consts.UP ->
                    Map.getMap().get((element.getPixelPositionY() - element.getSpeed()) / Screen.getTileSize()).get(element.getIndexPositionX()).isWall();
            case Consts.DOWN ->
                    Map.getMap().get((element.getPixelPositionY() + element.getSpeed()) / Screen.getTileSize() + 1).get(element.getIndexPositionX()).isWall();
            case Consts.LEFT ->
                    Map.getMap().get(element.getIndexPositionY()).get((element.getPixelPositionX() - element.getSpeed()) / Screen.getTileSize()).isWall();
            case Consts.RIGHT ->
                    Map.getMap().get(element.getIndexPositionY()).get((element.getPixelPositionX() + element.getSpeed()) / Screen.getTileSize() + 1).isWall();
            default -> false;
        };
    }

    public static boolean isTouching(Element a,Element b) {
        return a.getIndexPoint().x == b.getIndexPoint().x && a.getIndexPoint().y == b.getIndexPoint().y;
    }
}
