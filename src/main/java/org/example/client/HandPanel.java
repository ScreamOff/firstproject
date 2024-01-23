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
/// Klasa przedstawaijąca wyświetalanie ręki gracza
public class HandPanel extends JPanel {

    /// Konstruktor klasy HandPanel.
    public HandPanel(Hand hand) {
        setVisible(true);
        setOpaque(false);
        setLayout(new FlowLayout());
    }

    /// Metoda aktualizująca wygląd panelu na podstawie nowej ręki kart.
    public void updateHand(Hand hand) {
        /// Usuwa wszystkie komponenty z panelu
        removeAll();
        /// Wymusza ponowne sprawdzenie układu komponentów
        revalidate();
        /// Przerysowuje panel
        repaint();

        /// Wyświetla informacje o karcie
        log.info(hand.getCards().size() + "");
        /// Dodaje obrazy kart do panelu
        for (Card card : hand.getCards()) {
            log.info(card.toString());
            BufferedImage image;
            try {
                image = ImageIO.read(new File(card.getPathToPng()));
                /// Jeśli karta jest ograniczona(przeciwnika), użyj obrazu z tyłu karty
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
