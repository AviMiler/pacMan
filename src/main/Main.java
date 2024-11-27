package main;

import model.Map;
import model.elements.Element;
import control.GameLoop;
import view.GamePanel;
import view.MenuPanel;
import view.Screen;
import view.Window;

public class Main {
    public static void main(String[] args) {

        startGame();

    }
    public static void startGame() {
        Map.updateMap();
        Screen.updateScreen();
        MenuPanel menuPanel = (MenuPanel) Window.startWindow();
        menuPanel.moveToChoice();
    }
    public static void restartGame() {
        MenuPanel menuPanel = new MenuPanel();
        Window.setPanel(menuPanel);
        menuPanel.moveToChoice();
    }
}