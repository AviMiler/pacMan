package main;

import model.elements.Element;
import control.GameLoop;
import view.GamePanel;
import view.Window;

public class Main {
    public static void main(String[] args) {

        GameLoop gameLoop = new GameLoop();
        gameLoop.startGame();

    }
}