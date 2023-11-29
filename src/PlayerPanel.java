import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PlayerPanel extends JPanel {
    private JButton hitButton;
    private JButton standButton;
    private JLabel handLabel;
    private JLabel scoreLabel;

    private  Player player;

    public PlayerPanel(Player player) {
        this.player = player;
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
    public void updateHand() {
        for (Card card : player.getHand().getCards()) {
            try {
                BufferedImage image = ImageIO.read(new File(card.getPathToPng()));
                JLabel cardLabel = new JLabel(new ImageIcon(image));
                handLabel.add(cardLabel);

            } catch (IOException e) {
                System.out.println(card.getPathToPng());
                e.printStackTrace();
            }
        }

        // Metoda do aktualizacji wyświetlanych punktów
    /*public void updateScore(int score) {
        scoreLabel.setText("Punkty: " + score);
    }*/
    }}
