package view;
import model.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends Panel {

    int choice = 0;

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

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 1;
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 2;
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 3;
            }
        });

        this.add(button1);
        this.add(button2);
        this.add(button3);

    }
    public int getChoice() {
        while (choice == 0){
            System.out.print("");
        }
        return choice;
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
