package view;

import javax.swing.*;

public class Window {

    static JFrame window;
    static MyPanel panel;

    public static void startWindow() {

        window = new JFrame();
        panel = new MenuPanel();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setLocation(0, 0);
        window.add(panel);
        window.pack();

    }

    public static void setPanel(MyPanel panel) {
        window.remove(Window.panel);
        window.add(panel);
        window.pack();
        window.revalidate();
        window.repaint();
        Window.panel = panel;
    }

    public static MyPanel getPanel() {
        return Window.panel;
    }

    public static void close() {
        window.dispose();
    }
}
