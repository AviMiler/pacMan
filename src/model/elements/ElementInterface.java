package model.elements;

import java.awt.*;

public interface ElementInterface {

    Point getIndexPoint();

    Point getPixelPoint();

    Image getImage();

    int getScore();

    int getType();

    int getSpeed();

    int getSize();

    String getImagesPath();

    void setPixelPosition(int dir);

    void straitX();

    void straitY();

    void setImage(int direction);

    void beat();

    void updateScore(int score);

}
