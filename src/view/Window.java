package view;

import data.DB.DataBaseHandler;
import data.DB.ScoreUnit;
import data.ineerDB.Arrays;
import data.ineerDB.LinkedList;

import javax.swing.*;
import java.awt.*;

public class Window {

    static JFrame window;
    static Panel panel;

    public static Panel startWindow() {

        window = new JFrame();
        panel = new MenuPanel();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setLocation(0, 0);
        window.add(panel);
        window.pack();

        return panel;
    }

    public static void setPanel(Panel panel) {
        window.remove(Window.panel);
        window.add(panel);
        window.pack();
        Window.panel = panel;
    }

    public static Panel getPanel() {
        return Window.panel;
    }

    public static void removeAll() {
        window.removeAll();
    }

    public static void close(){
        window.dispose();
    }

}
