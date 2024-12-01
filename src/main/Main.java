package main;

import model.Map;
import model.elements.Element;
import control.GameLoop;
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

}