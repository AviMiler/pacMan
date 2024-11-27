package view;

import control.GameLoop;
import data.DB.DataBaseHandler;
import data.DB.ScoreUnit;
import data.HandleScores;
import data.ineerDB.LinkedList;
import main.Main;
import model.Map;
import model.elements.PacMan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EndGamePanel extends Panel implements KeyListener {

    int choice;
    boolean endGame=false,gameOver=false,newScore=false;
    LinkedList<ScoreUnit> scoresList;
    PacMan pacMan;
    String name="",note="";
    boolean delete;
    ScoreUnit scoreUnit = null;
    JButton button1;
    JButton button2;
    JButton button3;

    public EndGamePanel(int choice) {
        super();
        this.revalidate();
        this.setLayout(null);
        button1 = createStyledButton("NEXT",Screen.getScreenWidth()/4, 5*Screen.getScreenHeight() / 7,Screen.getScreenWidth()/2, Color.GREEN);
        button2 = createStyledButton("EXIT",Screen.getScreenWidth()/4, 5*Screen.getScreenHeight() / 7,Screen.getScreenWidth()/4, Color.RED);
        button3 = createStyledButton("RESTART",Screen.getScreenWidth()/2, 5*Screen.getScreenHeight() / 7,Screen.getScreenWidth()/4, Color.GREEN);
        setButtons();
        setVisible(true);
        this.addKeyListener(this);
        this.choice=choice;
        scoresList = DataBaseHandler.readScoresFromFile();
        if (choice!=0)
            repaint();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        switch (choice) {
            case 0:
                add(button1);
                Printer.printEndGame(graphics2D, endGame, gameOver);
                break;
            case 1:
                if (scoreUnit != null) {
                    Printer.printGotRecord(graphics2D, name);
                    this.requestFocusInWindow();
                    scoreUnit.setName(name);
                    break;
                } else {
                    choice = 2;
                }
            case 2:
                remove(button1);
                add(button2);
                add(button3);
                DataBaseHandler.saveScoresListToFile(scoresList);
                Printer.printScoresList(graphics2D, scoresList);
                break;
            case 3:
                Printer.printBye(graphics2D);
                break;
            case 4:
                Window.close();
                break;
            case 5:
                Main.startGame();
                return;
        }
        repaint();
    }

    public void endGame(PacMan pacMan){

        if (pacMan!=null) {
            this.pacMan = pacMan;

            if (pacMan.getLife() > 0)
                this.endGame = true;
            else
                this.gameOver = true;
            scoreUnit = HandleScores.addScore(scoresList, new ScoreUnit("t", pacMan.getScore()));
        }

    }

    public void readName(String note,boolean delete) {

        if (delete) {
            if (name.length() > 1) {
                name = name.substring(0, name.length() - 1);
            } else
                name = "";
        } else {
            name += note;
        }
        if (choice==2){
            scoreUnit.setName(name);
        }
    }

    /////////////////////////////button handler//////////////////////////////

    private JButton createStyledButton(String text,int x,int y,int width, Color backgroundColor) {

        JButton button = new JButton();
        button.setBounds(x, y,width, Screen.getScreenHeight()/7);
        button.setBackground(backgroundColor);
        button.setText(text);
        button.setForeground(Color.BLACK);
        button.setFont(Screen.customFont);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        return button;
    }

    private void setStyledButton(String text,int x,int y,int width, Color backgroundColor, JButton button) {

        button.setBounds(x, y,width, Screen.getScreenHeight()/7);
        button.setBackground(backgroundColor);
        button.setText(text);
        button.setForeground(Color.BLACK);
        button.setFont(Screen.customFont);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));

    }

    private void setButtons() {

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice++;//next
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice++;//exit
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice=5;//restart
            }
        });
    }

    /////////////////////////////keyFunctions////////////////////////////////

    @Override
    public void keyReleased(KeyEvent e) {
        note="";
        delete = e.getKeyCode() == KeyEvent.VK_BACK_SPACE;
        char t = e.getKeyChar();
        if (Character.isAlphabetic((t)))
            note+=t;
        readName(note,delete);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }
}
