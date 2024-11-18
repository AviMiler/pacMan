package model.elements;

import control.Collisions;
import model.Map;
import services.Consts;
import services.DB.Arrays;
import view.Screen;

import javax.swing.*;
import java.awt.*;

public class Ghost extends Element implements ElementInterface {

    private Point target;

    private int direction;

    public Ghost(int type){
        super(9+type,10);
        this.type=type;
        imagesPath = "C:\\Users\\User\\OneDrive\\מסמכים\\לימודים\\java\\IdeaProjects\\PacMan\\res\\ghosts\\"+type+"\\";
        image = new ImageIcon( imagesPath+0 + ".png").getImage();
        this.speed=1;
        this.target=new Point(0,0);
        this.direction=0;

    }

    public Point getTarget(){
        return target;
    }

    public int getDirection(){
        return direction;
    }

    public static Arrays<Ghost> initializeGhostList(){
        Arrays<Ghost> ghosts = new Arrays<>();
        for (int i = 1; i < 2; i++) {
            ghosts.add(new Ghost(i));
        }
        return ghosts;
    }

    public void calculateDirection(Point target){

        Point nextPosition = new Point(this.pixelPositionX,this.pixelPositionY);
        Point nextPosition2;
        int oldDirection = direction,newIndex;
        direction = 0;


        //set as up
        if (oldDirection != Consts.DOWN) {
            newIndex = this.getPixelPositionY() - this.getSpeed();
            if (!Collisions.isTouchWall(this.getIndexPositionX(),(newIndex)/Screen.getTileSize())) {
                nextPosition = new Point(this.getPixelPositionX(),newIndex);
                this.direction = Consts.UP;
            }
        }
        //check if left is shorter
        if (oldDirection != Consts.RIGHT) {
            newIndex = this.getPixelPositionX() - this.getSpeed();
            if (!Collisions.isTouchWall((newIndex)/Screen.getTileSize(),this.getIndexPositionY())) {
                nextPosition2 = new Point(newIndex, this.getPixelPositionY());
                if (checkIfBCloserThenA(nextPosition, nextPosition2,target)) {
                    nextPosition = nextPosition2;
                    this.direction = Consts.LEFT;
                }
            }
        }
        //check if down shorter
        if (oldDirection != Consts.UP) {
            newIndex = this.getPixelPositionY() + this.getSpeed();
            if (!Collisions.isTouchWall(this.getIndexPositionX(),(newIndex)/Screen.getTileSize()+1)) {
                nextPosition2 = new Point(this.getPixelPositionX(), newIndex);
                if (checkIfBCloserThenA(nextPosition, nextPosition2,target)) {
                    nextPosition = nextPosition2;
                    this.direction = Consts.DOWN;
                }
            }
        }

        //check if right sorter
        if (oldDirection != Consts.LEFT) {

            newIndex = this.getPixelPositionX() + this.getSpeed();
            if (!Collisions.isTouchWall((newIndex+Screen.getTileSize()-1)/Screen.getTileSize(),this.getIndexPositionY())) {
                nextPosition2 = new Point(newIndex, this.getPixelPositionY());
                if (checkIfBCloserThenA(nextPosition, nextPosition2,target)) {
                    this.direction = Consts.RIGHT;
                }
            }
        }
    }

    private boolean checkIfBCloserThenA(Point positionA, Point positionB,Point target){
        return positionA.distance(target)>positionB.distance(target);
    }
}
