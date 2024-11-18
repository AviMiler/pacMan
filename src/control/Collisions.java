package control;

import model.Map;
import model.elements.Element;
import services.DB.Arrays;
import model.elements.Permanent;

public class Collisions {

    public static boolean isTouchWall(int x, int y) {
        return Map.getMap().get(y).get(x).isWall();
    }
    public static boolean isTouching(Element a,Element b) {
        return a.getIndexPositionX() == b.getIndexPositionX() && a.getIndexPositionY() == b.getIndexPositionY();
    }

}
