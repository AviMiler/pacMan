package view;

import control.GameLoop;
import javax.swing.*;
import java.awt.*;

public class MenuPanel extends MyPanel {

    public MenuPanel() {

        super();
        this.setLayout(null);
        setButtons();
        setVisible(true);

    }

    private void setButtons(){

        JButton button1 = createStyledButton("Play", Screen.getScreenHeight()/7, Color.GREEN);
        JButton button2 = createStyledButton("Scores", (Screen.getScreenHeight()/7)*3, Color.YELLOW);
        JButton button3 = createStyledButton("Exit", (Screen.getScreenHeight()/7)*5, Color.RED);


        button1.addActionListener(_ -> b1());
        button2.addActionListener(_ -> b2());
        button3.addActionListener(_ -> b3());

        this.add(button1);
        this.add(button2);
        this.add(button3);

    }

    private void b1(){
        Window.setPanel(new GamePanel());
        new Thread(GameLoop::startGame).start();
    }
    private void b2(){
        Window.setPanel(new EndGamePanel(2));
    }
    private void b3(){
        Window.setPanel(new EndGamePanel(3));
    }

    private JButton createStyledButton(String text,int y, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBounds(Screen.getScreenWidth()/4, y,Screen.getScreenWidth()/2, Screen.getScreenHeight()/7);
        button.setBackground(backgroundColor);
        button.setForeground(Color.BLACK);
        button.setFont(Screen.customFont);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        return button;
    }

}
