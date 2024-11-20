package model.elements;

import control.Collisions;
import control.GameLoop;
import model.Map;
import services.Consts;
import services.DB.Arrays;
import services.Services;
import view.Screen;

import java.awt.*;

public class Ghost extends Element {

    private Point target;
    private int direction;
    public boolean eatable;

    //////////////////////////////constructors//////////////////////////////

    public Ghost(int type) {
        super(9 + type, 8);
        this.type = type;
        pictureType = type;
        imagesPath = "C:\\Users\\User\\OneDrive\\מסמכים\\לימודים\\java\\IdeaProjects\\PacMan\\res\\ghosts\\";
        setImage(0);
        this.speed = 1;
        this.eatable = false;
        this.target = new Point(0, 0);
        this.direction = 0;
        this.collisionMargin = Screen.getTileSize() / 2;

    }

    public static Arrays<Ghost> initializeGhostList() {
        Arrays<Ghost> ghosts = new Arrays<>();
        for (int i = 1; i < 5; i++) {
            ghosts.add(new Ghost(i));
        }
        return ghosts;
    }

    //////////////////////////////getters//////////////////////////////

    public Point getTarget() {
        return target;
    }

    public int getDirection() {
        return direction;
    }

    public boolean isEatable() {
        return eatable;
    }

    //////////////////////////////setters//////////////////////////////

    public void setToFrightenMode() {
        imageNum = 1;
        if (direction == 2)
            direction = 4;
        else if (direction == 1)
            direction = 3;
        else if (direction == 3)
            direction = 1;
        else if (direction == 4)
            direction = 2;
        state = Consts.FRIGHTENED;
        pictureType = Consts.FRIGHTENED;
    }

    public void setModeFromGameLoop(int mode){
        switch (mode) {
            case Consts.FRIGHTENED:
                if (state!=Consts.FRIGHTENED)
                    setToFrightenMode();
                break;
            case Consts.CHASE:
                if (state!=Consts.CHASE)
                    setToChaseMode();
                break;
            case Consts.SCATTER:
                if (state!=Consts.SCATTER)
                    setToScatterMode();
                break;
        }
    }

    public void setToChaseMode() {
        imageNum = 1;
        pictureType = type;
        state = Consts.CHASE;
    }

    public void setToScatterMode() {
        imageNum = 1;
        pictureType = type;
        state = Consts.SCATTER;
    }
    
    public void setToEatenMode(){
        pictureType=Consts.EATEN;
        state=Consts.EATEN;
        imageNum=3;
    }

                //////////////////////////////direction calculator//////////////////////////////

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


                //////////////////////////////targets calculator//////////////////////////////

    public void targetCalculator(PacMan pacMan,Ghost ghost) {
        switch (state) {
            case Consts.CHASE:
                chaseTargetCalculator(pacMan, ghost);
                break;
            case Consts.FRIGHTENED:
                frightenedTargetCalculator();
                break;
            case Consts.SCATTER:
                scatterTargetCalculator();
                break;
            case Consts.EATEN:
                eatenTargetCalculator();
        }
    }

    public void chaseTargetCalculator(PacMan pacMan,Ghost blinky){
        switch (this.type) {
            case Consts.BLINKY:
                this.target = new Point(pacMan.getPixelPositionX(), pacMan.getPixelPositionY());
                break;
            case Consts.INKY:
                this.target = calculateAmbush(pacMan, 4);
                break;
            case Consts.PINKY:
                this.target = calculateAmbush(pacMan, 2);
                this.target.x -= blinky.getPixelPositionX() - this.target.x;
                this.target.y -= blinky.getPixelPositionY() - this.target.y;
                break;
            case Consts.CLYDE:
                target = blinky.getTarget();
                if (this.getPixelPoint().distance(pacMan.pixelPoint)<8*Screen.getTileSize()){
                    target = new Point(0, Screen.getScreenHeight()-2*Screen.getTileSize());
                }

        }
    }

    public void scatterTargetCalculator () {
        switch (this.type) {
            case Consts.BLINKY:
                this.target = new Point(Screen.getScreenWidth()-Screen.getTileSize(), Screen.getTileSize());
                break;
            case Consts.INKY:
                this.target = new Point(Screen.getScreenWidth()-Screen.getTileSize(), Screen.getScreenHeight()-2*Screen.getTileSize());
                break;
            case Consts.PINKY:
                this.target = new Point(0, Screen.getTileSize());
                break;
            case Consts.CLYDE:
                target = new Point(0, Screen.getScreenHeight() - 2 * Screen.getTileSize());
        }
    }

    private void frightenedTargetCalculator(){
        target.x=Services.getRandomInt(0,Screen.getScreenWidth());
        target.y=Services.getRandomInt(0,Screen.getScreenHeight());
    }

    private void eatenTargetCalculator(){
        target.x= Map.getGhostsStartX();
        target.y=Map.getIndexPMStartY();
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
