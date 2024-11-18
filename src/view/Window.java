package view;

import javax.swing.*;

public class Window {

    static JFrame window;
    static GamePanel choicePanel;
    static MenuPanel menuPanel = new MenuPanel();

    public static GamePanel startWindow(){

        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setLocation(0, 0);

        window.add(menuPanel);
        window.pack();

        menu(menuPanel.getChoice());
        window.remove(menuPanel);
        window.add(choicePanel);
        window.pack();

        return choicePanel;

    }

    private static void menu (int choice){
        switch (choice) {
            case 1:
                choicePanel = new GamePanel();
                break;
            case 2:
                //choicePanel = new ScoresPanel();
                break;
            case 3:
                break;
        }

    }

}
