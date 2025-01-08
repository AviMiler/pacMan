package main;

import data.DB.ResourcesHandler;
import model.Map;
import view.*;

public class Main {
    public static void main(String[] args) {

        startGame();

    }

    public static void startGame() {

        Map.updateMap();
        Screen.updateScreen();
        ResourcesHandler.loadImages();
        Window.startWindow();

    }

    public static void restartGame() {
        Map.updateMap();
        Window.setPanel(new MenuPanel());
    }

}