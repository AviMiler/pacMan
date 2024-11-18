package model.elements;

import services.Consts;
import view.Screen;

import javax.swing.*;
import java.awt.*;

public class Element implements ElementInterface{

    protected int pixelPositionX,pixelPositionY,positionSize;
    protected int indexPositionX,indexPositionY;
    protected Image image;
    protected int imageNum = 1;
    protected int score;
    protected int speed;
    protected int type;
    protected int size;
    protected int beat;
    protected String imagesPath;

    public Element(int x, int y) {
        this.pixelPositionX = x * Screen.getTileSize();
        this.pixelPositionY = y * Screen.getTileSize();
    }

    public int getIndexPositionX() {
        return (this.pixelPositionX + Screen.getTileSize()/2) / Screen.getTileSize() ;
    }
    public int getIndexPositionY() {
        return (this.pixelPositionY + Screen.getTileSize()/2) / Screen.getTileSize();
    }

    public int getPixelPositionX() {
        return pixelPositionX;
    }
    public int getPixelPositionY() {
        return pixelPositionY;
    }

    public Image getImage() {
        return image;
    }

    public int getScore() {
        return score;
    }

    public int getType(){
        return type;
    }

    public int getSpeed(){
        return speed;
    }

    public int getSize(){
        return size;
    }

    public String getImagesPath(){
        return imagesPath;
    }

    public void setIndexPositionX(int x) {
        this.indexPositionX = x;
    }
    public void setIndexPositionY(int y) {
        this.indexPositionY = y;
    }

    public void setPixelPositionX(int x) {
        this.pixelPositionX = x;
    }
    public void setPixelPositionY(int y) {
        this.pixelPositionY = y;
    }

    public void straitX() {
        this.pixelPositionX = this.getIndexPositionX() * Screen.getTileSize();
    }
    public void straitY() {
        this.pixelPositionY = this.getIndexPositionY() * Screen.getTileSize();
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImage(int direction) {

        if (direction == Consts.RIGHT) {
            image = new ImageIcon(imagesPath + "R" + imageNum + ".png").getImage();
        }
        else if (direction == Consts.DOWN) {
            image = new ImageIcon(imagesPath + "D" + imageNum + ".png").getImage();
        }
        else if (direction == Consts.LEFT) {
            image = new ImageIcon(imagesPath + "L" + imageNum + ".png").getImage();
        }
        else if (direction == Consts.UP) {
            System.out.println(imagesPath + "U" + imageNum + ".png");
            image = new ImageIcon(imagesPath + "U" + imageNum + ".png").getImage();
        }
    }

    public void beat() {
        beat++;
        if (beat==10){
            if (imageNum==1)
                imageNum = 2;
            else if (imageNum==2)
                imageNum = 1;
            beat = 0;
        }
    }

    public void setSize(int size) {
        this.positionSize = size;
    }
    public void updateScore(int score) {
        this.score+=score;
    }

    public void upSpeed(){
        this.speed++;
    }

}
