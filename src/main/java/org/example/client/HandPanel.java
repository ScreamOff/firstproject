package org.example.client;

import lombok.extern.slf4j.Slf4j;
import org.example.base.Card;
import org.example.base.Hand;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public class HandPanel extends JPanel {


    public HandPanel(Hand hand) {

        setVisible(true);
        setOpaque(false);
        setLayout(new FlowLayout());
    }


    public void updateHand(Hand hand) {
        removeAll(); // Usuwa wszystkie komponenty z panelu
        revalidate(); // Wymusza ponowne sprawdzenie układu komponentów
        repaint(); // Przerysowuje panel

        // Dodaje obrazy kart do panelu
        log.info(hand.getCards().size() + "");
        for (Card card : hand.getCards()) {
            log.info(card.toString());
            BufferedImage image;
            try {
                image = ImageIO.read(new File(card.getPathToPng()));
                if (card.isRestricted()) {
                    image = ImageIO.read(new File(card.getPathToBack()));
                }
            } catch (IOException e) {
                log.error(card.toString());
                throw new RuntimeException(e);
            }
            add(new JLabel(new ImageIcon(image)));
        }
    }


}
