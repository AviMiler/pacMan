package view;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    public Panel() {
        this.setPreferredSize(new Dimension(Screen.getScreenWidth(),Screen.getScreenHeight()));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
}
