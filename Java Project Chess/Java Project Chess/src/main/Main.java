package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Start Menu");
        JLabel label = new JLabel("CHESS");
        label.setFont(new Font("Times New Roman", Font.BOLD, 50));
        label.setForeground(new Color(0x5D9948));
        label.setBounds(850,200,200,50);
        frame.add(label);

        frame.setSize(2000, 1000);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel panel = new JPanel();
        panel.setBackground(new Color(49, 46, 43));
        panel.setLayout(new GridBagLayout());


        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {

        panel.setLayout(null);

        JButton vsButton = new JButton("1v1");
        vsButton.setFont(new Font("Times New Roman", Font.BOLD, 28));
        vsButton.setBackground(new Color(93, 153, 72));
        vsButton.setForeground(Color.WHITE);
        vsButton.setFocusPainted(false);

        vsButton.setBounds(550, 450, 200, 100);
        panel.add(vsButton);



        vsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "VS Button Clicked");

                JFrame frame  = new JFrame();
                frame.getContentPane().setBackground(Color.lightGray);
                frame.setLayout(new GridBagLayout());
                frame.setMinimumSize(new Dimension(1000,1000));
                frame.setLocationRelativeTo(null);

                Board board = new Board();
                frame.add(board);

                frame.setVisible(true);

                ChessClock clock = new ChessClock();
                clock.setVisible(true);
                //idhar chess board open karne wala code dalna hai
            }
        });

        // Create and customize the "VS AI" button
        JButton vsAIButton = new JButton("VS AI");
        vsAIButton.setFont(new Font("Times New Roman", Font.BOLD, 28));
        vsAIButton.setBackground(new Color(55, 54, 54)); // Bootstrap success color
        vsAIButton.setForeground(Color.WHITE);
        vsAIButton.setFocusPainted(false);
       /*constraints.gridx = 1;
        constraints.gridy = 0;*/
        vsAIButton.setBounds(1150, 450, 200, 100);
        panel.add(vsAIButton);


        // Add action listener to the "VS AI" button
        vsAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "VS AI Button Clicked");
                // Add functionality to start a game vs AI
            }
        });







        /// comment


    }
}
