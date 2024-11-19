package model.elements;

import javax.swing.*;

import model.Map;
import services.Consts;
import view.Screen;

public class PacMan extends Element {

    private int life;

    public PacMan() {
        super(0,0);

        speed = 3;
        life = 3;
        imagesPath = "C:\\Users\\User\\OneDrive\\מסמכים\\לימודים\\java\\IdeaProjects\\PacMan\\res\\pacMan\\";
        image = new ImageIcon(imagesPath + 0 + ".png").getImage();
        score = 0;
        beat = 0;
        collisionMargin = 0;

    }

    public int getLife() {
        return life;
    }

    public void addLife() {
        life++;
    }

    public void setPosition() {
        this.setPixelPositionY(Map.getIndexPMStartY() * Screen.getTileSize());
        this.setPixelPositionX(Map.getIndexPMStartX() * Screen.getTileSize());
    }

    public void removeLife() {
        life--;
    }
}
