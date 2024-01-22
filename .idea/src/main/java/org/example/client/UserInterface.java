package org.example.client;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.base.Hand;
import org.example.base.HandPanel;
import org.example.event.Event;
import org.example.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class UserInterface extends Thread {
    ConcurrentLinkedQueue<Event> outbandEvents;
    Hand hand = new Hand();
    Hand enemy = new Hand();

    boolean end;


    final HandPanel handPanel = new HandPanel(hand);
    final HandPanel enemypanel = new HandPanel(enemy);

    JPanel buttons = new JPanel();
    UUID clientId;
    JButton hitButton = new JButton("Hit");
    JButton standButton = new JButton("Stand");

    JFrame frame;

    public UserInterface(ConcurrentLinkedQueue<Event> outbandEvents) {

        this.outbandEvents = outbandEvents;
        frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(50, 120, 60));
        frame.setLayout(new BorderLayout());
        frame.setSize(1000, 1000);
        int rows = 5, cols = 6;
        buttons.setLayout(new GridLayout(rows, cols));
        buttons.setOpaque(false);
        for (int i = 0; i < rows * cols - 1; i++) {
            if (i == 1) {
                buttons.add(hitButton);
                continue;
            }
            if (i == 4) {
                buttons.add(standButton);
                continue;
            }
            JButton spacer = new JButton();
            spacer.setVisible(false);
            buttons.add(spacer);
        }
        hitButton.addActionListener(e -> {
            // Obsługa zdarzenia dla przycisku "Hit"
            sendEvent(new HitEvent(clientId));
            handPanel.updateHand(hand);
            buttons.setVisible(false);

        });


        standButton.addActionListener(e -> {
            // Obsługa zdarzenia dla przycisku "Stand"
            //sendEvent(new StandEvent(clientId));
            buttons.setVisible(false);


        });


        hitButton.setVisible(true);
        standButton.setVisible(true);
        JPanel cards = new JPanel();
        cards.setLayout(new GridLayout(2, 1));
        frame.add(buttons, BorderLayout.SOUTH);
        frame.add(handPanel, BorderLayout.CENTER);
        frame.add(enemypanel, BorderLayout.NORTH);
        handPanel.updateHand(hand);
        enemypanel.updateHand(enemy);
        handPanel.setVisible(true);
        enemypanel.setVisible(true);
        buttons.setVisible(true);
        frame.setVisible(true);
    }


    @Override
    public void run() {


    }

    public void sendEvent(Event event) {
        this.outbandEvents.add(event);
    }

    @SneakyThrows
    private void update() {
        SwingUtilities.invokeAndWait( () -> {
                handPanel.updateHand(hand);
                enemypanel.updateHand(enemy);
            }
        );
    }

    private void checkforpoints(Hand hand) {
        if (hand.calculateCardValue() > 21) {
            buttons.setVisible(false);
            sendEvent(new LoseEvent());
            this.end = true;
        }
    }

//    private void restart() {
//        this.hand = new Hand();
//        this.enemy = new Hand();
//        update();
//    }

    public void handleEvent(Event event) {
        log.info(event.toString());
        // jezeli to zostanie wywołane to wykana akcje

        if (event instanceof ConnectionAcceptEvent) {
            this.clientId = ((ConnectionAcceptEvent) event).getId();
            this.sendEvent(new ReadyEvent(this.clientId));


        } else if (event instanceof CardEvent) {
            ((CardEvent) event).consume(clientId, hand, enemy);
            if (enemy.size() == 1) {
                enemy.block();
            }
            checkforpoints(hand);
        } else if (event instanceof PingEvent) {
            buttons.setVisible(true);

        } else if (event instanceof LoseEvent) {
            enemy.show();
            buttons.setVisible(false);
            var scoreTextArea = new JTextArea();
            scoreTextArea.setText("Przeciwnik: "+ enemy.calculateCardValue() + " Ja: " + hand.calculateCardValue());
            scoreTextArea.setVisible(true);
            frame.add(scoreTextArea, BorderLayout.SOUTH);

        } else if (event instanceof ResultEvent) {
            sendEvent(new Ending(clientId, hand));

        }
        update();
    }
}
