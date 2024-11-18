package model.elements;

import services.Consts;
import view.Screen;

import javax.swing.*;
import java.awt.*;

public interface ElementInterface {

    public Point getIndexPoint();
    public Point getPixelPoint();

    public Image getImage();

    public int getScore();
    public int getType();

    public int getSpeed();

    public int getSize();

    public String getImagesPath();

    public void setIndexPositionX(int x);
    public void setIndexPositionY(int y);

    public void setPixelPosition(int dir);

    public void straitX();
    public void straitY();

    public void setImage(int direction);

    public void beat();

    public void updateScore(int score);

    public void upSpeed();

}
