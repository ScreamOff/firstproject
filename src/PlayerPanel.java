import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayerPanel {
    private JButton hitButton;
    private JButton standButton;

    private JPanel handPanel;  // Używamy panelu dla kart z FlowLayout
    private JLabel scoreLabel;

    private Player player;

    private JPanel playerPanel;

    private JPanel buttonsPanel;

    private JPanel spacePanel;

    private JLabel cardLabel;


    public JPanel getPlayerPanel() {
        return playerPanel;
    }

    public PlayerPanel(Player player) {
        this.player = player;

        // Inicjalizacja przycisków
        hitButton = new JButton("Dobierz");
        standButton = new JButton("Pasuj");

        // Inicjalizacja etykiet
        scoreLabel = new JLabel("Punkty: 0");
        Font czcionka = new Font(Font.SANS_SERIF, Font.BOLD,  30);
        scoreLabel.setFont(czcionka);

        // Ustawienia layoutu
        playerPanel = new JPanel(new BorderLayout());
        playerPanel.setOpaque(false);

        // Inicjalizacja panelu dla kart z FlowLayout
        handPanel = new JPanel();
        handPanel.setLayout(new FlowLayout());
        handPanel.setOpaque(false);

        // Dodanie komponentów do panelu
        playerPanel.add(handPanel, BorderLayout.CENTER);
        playerPanel.add(scoreLabel, BorderLayout.NORTH);
        spacePanel = new JPanel();
        spacePanel.setOpaque(false);
        buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(hitButton);
        buttonsPanel.add(standButton);
        playerPanel.add(buttonsPanel, BorderLayout.SOUTH);



        // Dodanie obsługi zdarzeń
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.addCardToHand(player.deck.drawCard());
                updateHand();
                updateScore();

            }
        });

        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setStand(true);
                System.out.println(player.isStand());
            }
        });
    }


    // Metoda do aktualizacji GUI w zależności od ręki gracza
    public void updateHand() {
        handPanel.removeAll(); // Usuń poprzednie komponenty z panelu dla kart przed dodaniem nowych

        for (Card card : player.getHand().getCards()) {
            try {
                BufferedImage image = ImageIO.read(new File(card.getPathToPng()));
                cardLabel = new JLabel(new ImageIcon(image));
                handPanel.add(cardLabel);


            } catch (IOException e) {
                System.out.println(card.getPathToPng());
                e.printStackTrace();
            }
        }

        playerPanel.revalidate(); // Odśwież układ panelu
        playerPanel.repaint(); // Przerysuj panel
    }

    // Metoda do aktualizacji wyświetlanych punktów
    public void updateScore() {
        int score = player.calculateHandValue();
        scoreLabel.setText("Punkty: " + score);
        if(score >21)
        {
            hitButton.setVisible(false);
            standButton.setVisible(false);
            scoreLabel.setText("BUSTED");
        }

    }
}
