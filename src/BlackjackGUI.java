import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



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

        JPanel bpanel = new JPanel();
        Font font = new Font("SansSerif", Font.BOLD, 24);
        JButton button1 = new JButton("Dobierz");
        button1.setBounds(390,200,95,30);
        JButton button2 = new JButton("Pasuj");
        button2.setBounds(500,200,95,30);
        JLabel text = new JLabel();
        JLabel text2= new JLabel();
        text2.setFont(font);
        bpanel.add(text2);
        text2.setBounds(850,900,200,90);
        // Utwórz panel na wyświetlenie kart
        JPanel cardPanel = new JPanel();
        cardPanel.setOpaque(false); // Ustawienie panelu jako przezroczystego || Zasłaniał "stół"
        // Pobierz 5 kart z talii na rękę
        Hand hand = new Hand();
        List<Card> cardsToDisplay = hand.getCards();


        // Wyświetl obrazy kart na panelu
        for (int i = 0; i < 2; i++) {
            Card card = deck.drawCard();
            if (card != null) {

                hand.addCard(card);
            }
            text2.setText("Puntky: "+ hand.calculateCardValue());
        }

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                text.setBounds(400,350,400,50);
                text.setText("Dobrales");
                text.setFont(font);
                Card card = deck.drawCard();
                if (card != null) {
                    hand.addCard(card);
                }

                BufferedImage image = null;
                try {
                    image = ImageIO.read(new File(card.getPathToPng()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                JLabel cardLabel2 = new JLabel(new ImageIcon(image));
                cardPanel.add(cardLabel2);
                text2.setText("Puntky: "+ hand.calculateCardValue());
            }
        });


        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text.setBounds(400,350,400,50);
                text.setText("Spasowales");
                text.setFont(font);

            }
        });


        //wyswietlanie kart
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




        frame.add(button1);
        frame.add(button2);
        frame.add(bpanel);
        frame.add(text);
        frame.add(text2);
        frame.setLayout(new BorderLayout());
        frame.add(cardPanel, BorderLayout.CENTER);
        frame.setSize(1000, 1000);


        frame.setVisible(true);
    }
}
