package org.example.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/// Klasa reprezentująca okno do wprowadzania zakładu.
public class BettingForm extends JFrame {

    /// Pole tekstowe do wprowadzenia kwoty zakładu.
    private JTextField betAmountField;

    /// Przycisk do potwierdzania zakładu.
    private JButton betButton;

    /// Referencja do interfejsu użytkownika.
    private UserInterface userInterface;

    /// Identyfikator gracza.
    private int id_gracza;

    /// Konstruktor klasy BettingForm.
    /// @param id_gracza Identyfikator gracza.
    /// @param userInterface Referencja do interfejsu użytkownika.
    public BettingForm(int id_gracza, UserInterface userInterface) {
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

    /// Metoda obsługująca potwierdzenie zakładu.
    private void placeBet() {
        try {
            int betAmount = Integer.parseInt(betAmountField.getText());
            JOptionPane.showMessageDialog(this, "Bet placed successfully: " + betAmount, "Success", JOptionPane.INFORMATION_MESSAGE);
            userInterface.onLogin(id_gracza, betAmount);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid bet amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }
}
