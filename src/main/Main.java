package main;

import model.Map;
import view.*;

public class Main {
    public static void main(String[] args) {

        startGame();

    }

    public static void startGame() {

        Map.updateMap();
        Screen.updateScreen();
        Window.startWindow();

    }

    public static void restartGame() {
        Window.setPanel(new MenuPanel());
    }

}