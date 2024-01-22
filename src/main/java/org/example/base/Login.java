package org.example.base;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        setTitle("Logowanie");
        setSize(300, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        panel.add(new JLabel("Login:"));
        panel.add(usernameField);
        panel.add(new JLabel("Hasło:"));
        panel.add(passwordField);

        JButton loginButton = new JButton("Zaloguj");
        JButton registerButton = new JButton("Zarejestruj się");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRegister();
            }
        });

        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    private void onLogin() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        if (username.trim().isEmpty() || String.valueOf(password).isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pole loginu i hasła nie mogą być puste", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection connection = BazaDanychPolaczenie.connect();
            String query = "SELECT * FROM Gracze WHERE nazwa = ? AND haslo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, String.valueOf(password));

                ResultSet result = preparedStatement.executeQuery();

                if (result.next()) {
                    JOptionPane.showMessageDialog(this, "Zalogowano pomyślnie", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Błędne hasło lub login", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private void onRegister() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        if (username.trim().isEmpty() || String.valueOf(password).isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pole loginu i hasła nie mogą być puste", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection connection = BazaDanychPolaczenie.connect();
            String query = "INSERT INTO Gracze(nazwa, haslo) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, String.valueOf(password));
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {

                    JOptionPane.showMessageDialog(this, "Rejestracja zakończona pomyślnie", "Sukces", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(this, "Błąd podczas rejestracji", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
