package view;

import data.DB.DataBaseHandler;
import data.DB.ScoreUnit;
import data.ineerDB.Arrays;
import data.ineerDB.LinkedList;

import javax.swing.*;

public class Window {

    static JFrame window;
    static Panel choicePanel;
    static Panel menuPanel = new MenuPanel();

    public static Panel startWindow() {

        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setLocation(0, 0);
        window.add(menuPanel);
        window.pack();

        menu(((MenuPanel) menuPanel).getChoice());
        return replacePanel(menuPanel, choicePanel);

    }

    private static void menu (int choice){
        switch (choice) {
            case 1:
                choicePanel = new GamePanel();
                break;
            case 2:
                choicePanel = null;
                break;
            case 3:
                choicePanel = null;
                break;
        }
    }

    public static Panel replacePanel(Panel panel1, Panel panel2) {

        if (panel1 != null && panel2 != null) {
            window.remove(panel1);
            window.add(panel2);
            window.pack();
        }
        return panel2;
    }

    public static void close(){
        window.dispose();
    }

}
