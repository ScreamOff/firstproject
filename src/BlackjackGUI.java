import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;
import javax.swing.Timer;



public class BlackjackGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
    private static void display(PlayerPanel cardPanel,List<Card> cardsToDisplay){
        for (Card card : cardsToDisplay) {
            try {
                BufferedImage image = ImageIO.read(new File(card.getPathToPng()));
                JLabel cardLabel = new JLabel(new ImageIcon(image));
                //cardPanel.add(cardLabel);

            } catch (IOException e) {
                System.out.println(card.getPathToPng());
                e.printStackTrace();
            }
        }
    }
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Blackjack - Wyświetlenie 5 kart na ręce");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(50, 120, 60));
        Deck deck = new Deck();
        Player gracz = new Player(deck);
        PlayerPanel panel =new PlayerPanel(gracz);
        panel.updateHand();







        //wyswietlanie kart
        /*
        frame.add(button1);
        frame.add(button2);
        frame.add(bpanel);
        frame.add(text);
        frame.add(text2);
        frame.add(text3);
        */
        //frame.add(panel);
        frame.setLayout(new BorderLayout());
        frame.add(panel.getPlayerPanel());
        frame.setSize(1000, 1000);


        frame.setVisible(true);
    }
}
