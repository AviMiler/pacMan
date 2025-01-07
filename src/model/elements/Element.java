package model.elements;

import model.Map;
import services.Consts;
import view.Screen;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class Element implements ElementInterface{

    protected Point pixelPoint,indexPoint;
    protected Image image;
    protected int imageNum = 1;
    protected int score;
    protected int speed;
    protected int type;
    protected int pictureType;
    protected int state;
    protected int size;
    protected int collisionMargin;
    protected int beat;
    protected String imagesPath;
    protected int direction;

    public Element(int x, int y) {
        this.pixelPoint = new Point(x * Screen.getTileSize() ,y * Screen.getTileSize());
    }

    public int getState(){
        return state;
    }

    public Point getIndexPoint() {
        return new Point((this.pixelPoint.x + Screen.getTileSize()/2) / Screen.getTileSize(),(this.pixelPoint.y + Screen.getTileSize()/2)/ Screen.getTileSize());
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

    public int getIndexPositionX(){
        if (getIndexPoint().x>= Map.getTilesWidth())
            return Map.getTilesWidth()-1;
        else if (getIndexPoint().x<0)
            return 0;
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
                if (pixelPoint.x - this.speed == 0) {
                    this.pixelPoint.x = Screen.getScreenWidth();
                }
                else
                    this.pixelPoint.x -= this.speed;
                break;
            case Consts.RIGHT:
                if (pixelPoint.x + this.speed >= Screen.getScreenWidth()) {
                    this.pixelPoint.x = 0;
                }
                else
                    this.pixelPoint.x += this.speed;
                break;
            case Consts.START:
                this.pixelPoint.x = 0;
                break;
            case Consts.END:
                this.pixelPoint.x = Screen.getScreenWidth();
                break;
        }
        this.setImage(dir);
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
                if (newPosition.x - this.speed <= 0) {
                    newPosition.x = Screen.getScreenWidth() - Screen.getTileSize();
                }
                else
                    newPosition.x -= element.speed;
                break;
            case Consts.RIGHT:
                if (newPosition.x + this.speed >= Screen.getScreenWidth()) {
                    newPosition.x = 0;
                }
                else
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
    public void straitXY(){
        this.straitX();
        this.straitY();
    }

    public void straitXOrY(){
        if (this.direction == Consts.UP || this.direction == Consts.DOWN)
            this.straitX();
        if (this.direction == Consts.LEFT || this.direction == Consts.RIGHT)
            this.straitY();
    }

    public void setImage(int direction) {

        String [] directions = {"0","R","D","L","U","E","F","nothing"};
        String dir = directions[direction];
        if (pictureType==Consts.FRIGHTENED)
            dir = directions[Consts.FRIGHTENED];
        image=loadImage(imagesPath + this.pictureType +"\\"+ dir +imageNum+ ".png");
    }

    private Image loadImage(String relativePath) {
        // First attempt: Try loading from the classpath
        URL resource = getClass().getResource(relativePath);
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        }

        // Second attempt: Fallback to file system (for development or non-JAR execution)
        File file = new File(relativePath);
        if (file.exists()) {
            return new ImageIcon(file.getAbsolutePath()).getImage();
        }

        // If the image is not found, silently return null or a default image
        return null;  // You can return a default image if needed
    }

    public void beat() {
        beat++;
        if (beat==10){
            changeImage();
            beat = 0;
        }
    }

    public void changeImage(){
        if (imageNum==1)
            imageNum = 2;
        else if (imageNum==2)
            imageNum = 1;
    }

    public void updateScore(int score) {
        this.score+=score;
    }

}
