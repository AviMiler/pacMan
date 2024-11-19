package model.elements;

import services.Consts;
import view.Screen;

import javax.swing.*;
import java.awt.*;

public class Element implements ElementInterface{

    protected Point pixelPoint,indexPoint;
    protected Image image;
    protected int imageNum = 1;
    protected int score;
    protected int speed;
    protected int type;
    protected int size;
    protected int collisionMargin;
    protected int beat;
    protected String imagesPath;
    protected int direction;

    public Element(int x, int y) {
        this.pixelPoint = new Point(x * Screen.getTileSize() ,y * Screen.getTileSize());
    }

    public Point getIndexPoint() {
        return new Point((this.pixelPoint.x + Screen.getTileSize()/2+1) / Screen.getTileSize(),(this.pixelPoint.y + Screen.getTileSize()/2+1)/ Screen.getTileSize());
    }

    public Point getPixelPoint() {
        return pixelPoint;
    }

    public int getPixelPositionX(){
        return pixelPoint.x;
    }

    public int getPixelPositionY(){
        return pixelPoint.y;
    }

    public int getMiddlePixelX(){
        return pixelPoint.x  + (Screen.getTileSize()/2);
    }
    public int getMiddlePixelY(){
        return pixelPoint.y  + (Screen.getTileSize()/2);
    }

    public int getCollisionMargin() {
        return collisionMargin;
    }

    public int getIndexPositionX(){
        return getIndexPoint().x;
    }

    public int getIndexPositionY(){
        return getIndexPoint().y;
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
        this.indexPoint.x = x;
    }
    public void setIndexPositionY(int y) {
        this.indexPoint.y = y;
    }

    public void setPixelPositionX(int x) {
        this.pixelPoint.x = x;
    }
    public void setPixelPositionY(int y) {
        this.pixelPoint.y = y;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public void setPixelPosition(int dir) {

        switch (dir) {
            case Consts.UP:
                this.pixelPoint.y -= this.speed;
                break;
            case Consts.DOWN:
                this.pixelPoint.y += this.speed;
                break;
            case Consts.LEFT:
                this.pixelPoint.x -= this.speed;
                break;
            case Consts.RIGHT:
                this.pixelPoint.x += this.speed;
                break;
            case Consts.START:
                this.pixelPoint.x = 0;
                break;
            case Consts.END:
                this.pixelPoint.x = Screen.getScreenWidth() - Screen.getTileSize();
                break;
        }
    }

    public Point setPixelPosition(Element element , int dir) {

        Point newPosition = new Point(this.getPixelPositionX(),this.getPixelPositionY());

        switch (dir) {
            case Consts.UP:
                newPosition.y -= element.speed;
                break;
            case Consts.DOWN:
                newPosition.y += element.speed;
                break;
            case Consts.LEFT:
                newPosition.x -= element.speed;
                break;
            case Consts.RIGHT:
                newPosition.x += element.speed;
                break;
            case Consts.START:
                newPosition.x = 0;
                break;
            case Consts.END:
                newPosition.x = Screen.getScreenWidth() - Screen.getTileSize();
                break;
        }
        return newPosition;
    }

    public void straitX() {
        this.pixelPoint.x= this.getIndexPoint().x * Screen.getTileSize();
    }
    public void straitY() {
        this.pixelPoint.y=this.getIndexPoint().y * Screen.getTileSize();
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

    public void updateScore(int score) {
        this.score+=score;
    }

    public void upSpeed(){
        this.speed++;
    }
}
