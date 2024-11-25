package model.elements;

import javax.swing.*;

import model.Map;
import services.Consts;
import view.Screen;

public class PacMan extends Element {

    private int life;
    private int ghostEatenCnt;

    public PacMan() {
        super(0, 0);

        speed = 3;
        type = 1;
        pictureType = type;
        state = 0;
        life =1;
        imagesPath = "C:\\Users\\User\\OneDrive\\מסמכים\\לימודים\\java\\IdeaProjects\\PacMan\\res\\pacMan\\";
        setImage(0);
        score = 0;
        beat = 0;
        collisionMargin = 0;
        ghostEatenCnt = 1;

    }

    public int getLife() {
        return life;
    }

    public void addLife() {
        life++;
    }

    public int getGhostEatenCnt() {
        return ghostEatenCnt;
    }

    public void addToGhostEatenCnt() {
        ghostEatenCnt++;
    }

    public void resetGhostEatenCnt() {
        ghostEatenCnt = 1;
    }

    public void setPosition() {
        this.setPixelPositionY(Map.getIndexPMStartY() * Screen.getTileSize());
        this.setPixelPositionX(Map.getIndexPMStartX() * Screen.getTileSize());
        this.direction=0;
    }

    public void eaten(){
        removeLife();
        setImage(Consts.EATEN);
        state=Consts.EATEN;
    }

    public void removeLife() {
        life--;
    }
}
