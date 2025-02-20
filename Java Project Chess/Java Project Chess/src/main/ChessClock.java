package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessClock extends JFrame {
    private JLabel whiteClockLabel, blackClockLabel;
    private Timer whiteTimer, blackTimer;
    private int whiteTime = 300, blackTime = 300;
    private boolean whiteTurn = true;
    private Board c;

    public ChessClock() {
        setTitle("Chess Clock");
        setSize(300, 300);
        getContentPane().setBackground(new Color(93, 153, 72));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        whiteClockLabel = new JLabel("White: 05:00", SwingConstants.CENTER);
        blackClockLabel = new JLabel("Black: 05:00", SwingConstants.CENTER);
        add(whiteClockLabel);
        add(blackClockLabel);

        whiteTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (whiteTime > 0) {
                    whiteTime--;
                    updateClockLabel(whiteClockLabel, whiteTime);
                }
            }
        });

        blackTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (blackTime > 0) {
                    blackTime--;
                    updateClockLabel(blackClockLabel, blackTime);
                }
            }
        });

        whiteTimer.start();
    }

    private void updateClockLabel(JLabel label, int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        label.setText(String.format("%s: %02d:%02d", label.getText().split(":")[0], minutes, seconds));
    }

    public void switchTurns() {
        if (c.isWhiteToMove) {
            whiteTimer.stop();
            blackTimer.start();
        } else {
            blackTimer.stop();
            whiteTimer.start();
        }
        whiteTurn = !whiteTurn;
    }

    public void validMove() {//ye function dosre valid move function me call karna hai

        switchTurns();
    }
}