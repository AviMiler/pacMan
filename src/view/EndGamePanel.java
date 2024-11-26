package view;

import control.GameLoop;
import data.DB.DataBaseHandler;
import data.DB.ScoreUnit;
import data.HandleScores;
import data.ineerDB.LinkedList;
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
    JButton button1 = createStyledButton("NEXT",345,Color.BLUE);
    JButton button2 = createStyledButton("END",400,Color.RED);

    public EndGamePanel(int choice) {
        super();
        this.revalidate();
        this.setLayout(null);
        setButtons();
        setVisible(true);
        this.addKeyListener(this);
        this.choice=choice;

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        switch (choice) {
            case 0:
                Printer.printEndGame(graphics2D, endGame, gameOver);
                break;
            case 1:
                if (scoreUnit != null) {
                    Printer.printGotRecord(graphics2D, name);
                    this.requestFocusInWindow();
                    repaint();
                    scoreUnit.setName(name);
                    break;
                } else {
                    choice = 2;
                }
            case 2:
                DataBaseHandler.saveScoresListToFile(scoresList);
                button1.setText("EXIT");
                this.add(button2);
                Printer.printScoresList(graphics2D, scoresList);
                break;
            case 3:
                Printer.printBye(graphics2D);
                break;
            case 4:
                Window.close();
            case 100:
        }
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

    private void setButtons() {
        button1 = createStyledButton("NEXT", 5*Screen.getScreenHeight() / 7, Color.GREEN);
        button2 = createStyledButton("RESTART", 5*Screen.getScreenHeight() / 8, Color.RED);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice++;
                repaint();
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice=100;
                repaint();
            }
        });

        this.add(button1);
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
