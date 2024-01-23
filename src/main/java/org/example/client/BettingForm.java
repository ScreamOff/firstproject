package org.example.client;

import org.example.client.UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BettingForm extends JFrame {

    private JTextField betAmountField;
    private JButton betButton;
    private UserInterface userInterface;

    private int id_gracza;

    public BettingForm(int id_gracza,UserInterface userInterface) {
        this.userInterface = userInterface;
        this.id_gracza = id_gracza;
        setTitle("Betting Form");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel betLabel = new JLabel("Enter Bet Amount:");
        betAmountField = new JTextField();
        betButton = new JButton("Place Bet");

        betButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeBet();
            }
        });

        panel.add(betLabel);
        panel.add(betAmountField);
        panel.add(betButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void placeBet() {
        try {
            int betAmount = Integer.parseInt(betAmountField.getText());
            JOptionPane.showMessageDialog(this, "Bet placed successfully: " + betAmount, "Success", JOptionPane.INFORMATION_MESSAGE);
            userInterface.onLogin(id_gracza,betAmount);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid bet amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }
}

