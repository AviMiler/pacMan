package model.elements;

import control.Collisions;
import services.Consts;
import services.DB.Arrays;
import view.GamePanel;
import view.Screen;

import javax.swing.*;
import java.awt.*;

public class Ghost extends Element{

    private Point target;

    private int direction;

    public Ghost(int type){
        super(9+type,8);
        this.type=type;
        imagesPath = "C:\\Users\\User\\OneDrive\\מסמכים\\לימודים\\java\\IdeaProjects\\PacMan\\res\\ghosts\\"+type+"\\";
        image = new ImageIcon( imagesPath+0 + ".png").getImage();
        this.speed=1;
        this.target=new Point(0,0);
        this.direction=0;
        collisionMargin = Screen.getTileSize()/2;

    }

    public Point getTarget(){
        return target;
    }

    public void setTarget(Point target){
        this.target=target;
    }

    public int getDirection(){
        return direction;
    }

    public static Arrays<Ghost> initializeGhostList(){
        Arrays<Ghost> ghosts = new Arrays<>();
        for (int i = 1; i < 3; i++) {
            ghosts.add(new Ghost(i));
        }
        return ghosts;
    }

    public void calculateDirection(Point target) {

        Arrays<Integer> possibleDirections = possibleDirections();
        Point nextPosition1;
        Point nextPosition2;

        if (possibleDirections.size()>0) {
            nextPosition1 = setPixelPosition(this, possibleDirections.get(0));
            this.direction = possibleDirections.get(0);

            for (int i = 1; i < possibleDirections.size(); i++) {
                nextPosition2 = setPixelPosition(this, possibleDirections.get(i));
                if (checkIfBCloserThenA(nextPosition1, nextPosition2, target)) {
                    nextPosition1.x = nextPosition2.x;
                    nextPosition1.y = nextPosition2.y;
                    this.direction = possibleDirections.get(i);
                }
            }
        }
    }

    private Arrays<Integer> possibleDirections(){
        final Arrays<Integer> possibleDirections = new Arrays<>();
        for (int i = 4; i > 0; i--) {
            if (this.direction+2 != i && this.direction - 2!= i){
                if (!Collisions.isIndexTouchWall(this,i) && isInJunction())
                    possibleDirections.add(i);
            }
        }
        return possibleDirections;
    }

    private boolean checkIfBCloserThenA(Point positionA, Point positionB,Point target){
        return positionA.distance(target)>positionB.distance(target);
    }

    private boolean isInJunction() {
        return this.pixelPoint.x % Screen.getTileSize() == 0 && this.pixelPoint.y % Screen.getTileSize() == 0;
    }

    public void strait(){
        if (this.direction==Consts.RIGHT || this.direction==Consts.LEFT){
            this.straitY();
        }
        if (this.direction==Consts.UP || this.direction==Consts.DOWN){
            this.straitX();
        }
    }

    public void chaseTargetCalculator(PacMan pacMan){
        switch (this.type){
            case Consts.BLINKY:
                this.target=new Point(pacMan.getPixelPositionX(),pacMan.getPixelPositionY());
                break;
            case Consts.INKY:
                this.target=calculateAmbush(pacMan,4);
                break;
        }
    }

    private Point calculateAmbush(PacMan pacMan,int distance){
       return switch (pacMan.getDirection()) {
            case Consts.RIGHT ->
                    new Point(pacMan.getPixelPositionX() + distance * Screen.getTileSize(), pacMan.getPixelPositionY());
            case Consts.LEFT ->
                    new Point(pacMan.getPixelPositionX() - distance * Screen.getTileSize(), pacMan.getPixelPositionY());
            case Consts.UP ->
                    new Point(pacMan.getPixelPositionX() - distance * Screen.getTileSize(), pacMan.getPixelPositionY() - distance * Screen.getTileSize());
            case Consts.DOWN ->
                    new Point(pacMan.getPixelPositionX(), pacMan.getPixelPositionY() + distance * Screen.getTileSize());
            default -> target;
        };
    }

}
