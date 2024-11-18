package model.elements;

import view.Screen;

import java.awt.*;

public interface ElementInterface {

    public int getIndexPositionX();
    public int getIndexPositionY();

    public int getPixelPositionX();
    public int getPixelPositionY();

    public Image getImage();

    public int getScore();
    public int getType();

    public int getSpeed();

    public int getSize();

    public String getImagesPath();

    public void setIndexPositionX(int x);
    public void setIndexPositionY(int y);

    public void setPixelPositionX(int x);
    public void setPixelPositionY(int y);

    public void straitX();
    public void straitY();

    public void setImage(Image image);
    public void setSize(int size);
    public void updateScore(int score);

    public void upSpeed();

}
