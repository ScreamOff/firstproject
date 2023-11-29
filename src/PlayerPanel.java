import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PlayerPanel extends JPanel {
    private JButton hitButton;
    private JButton standButton;
    private JLabel handLabel;
    private JLabel scoreLabel;

    public PlayerPanel() {
        // Inicjalizacja przycisków
        hitButton = new JButton("Dobierz");
        standButton = new JButton("Pasuj");

        // Inicjalizacja etykiet
        handLabel = new JLabel("Ręka:");
        scoreLabel = new JLabel("Punkty: 0");

        // Ustawienia layoutu
        setLayout(new GridLayout(2, 2));

        // Dodanie komponentów do panelu
        add(handLabel);
        add(scoreLabel);
        add(hitButton);
        add(standButton);

        // Dodanie obsługi zdarzeń
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obsługa naciśnięcia przycisku "Dobierz"
                // Dodaj logikę dla "Dobierz"
            }
        });

        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obsługa naciśnięcia przycisku "Pasuj"
                // Dodaj logikę dla "Pasuj"
            }
        });
    }

    // Metoda do aktualizacji GUI w zależności od ręki gracza
    public void updateHand(List<Card> cards) {
        StringBuilder handText = new StringBuilder("Ręka: ");
        for (Card card : cards) {
            handText.append(card.toString()).append(" ");
        }
        handLabel.setText(handText.toString());
    }

    // Metoda do aktualizacji wyświetlanych punktów
    public void updateScore(int score) {
        scoreLabel.setText("Punkty: " + score);
    }
}
