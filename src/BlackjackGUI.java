import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;

public class BlackjackGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Blackjack - Wyświetlenie 5 kart na ręce");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(50, 120, 60));

        // Utwórz talie kart
        Deck deck = new Deck();


        // Pobierz 5 kart z talii na rękę
        Hand hand = new Hand();
        for (int i = 0; i < 5; i++) {
            Card card = deck.drawCard();
            if (card != null) {
                hand.addCard(card);
            }
        }

        // Utwórz panel na wyświetlenie kart
        JPanel cardPanel = new JPanel();

        // Wyświetl obrazy kart na panelu
        List<Card> cardsToDisplay = hand.getCards();
        for (Card card : cardsToDisplay) {
            try {
                BufferedImage image = ImageIO.read(new File(card.getPathToPng()));
                JLabel cardLabel = new JLabel(new ImageIcon(image));
                cardPanel.add(cardLabel);
            } catch (IOException e) {
                System.out.println(card.getPathToPng());
                e.printStackTrace();
            }
        }

        frame.setLayout(new BorderLayout());
        frame.add(cardPanel, BorderLayout.CENTER);
        frame.setSize(800, 200);
        frame.setVisible(true);
    }
}
