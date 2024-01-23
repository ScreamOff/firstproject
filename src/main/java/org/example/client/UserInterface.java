package org.example.client;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.base.BazaDanychPolaczenie;
import org.example.base.Hand;
import org.example.event.*;
import org.example.event.init.ConnectionAcceptEvent;
import org.example.event.init.Event;
import org.example.event.init.ReadyEvent;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class UserInterface extends Thread {
    ConcurrentLinkedQueue<Event> outbandEvents;
    Hand hand = new Hand();
    Hand enemy = new Hand();

    int id_gracza, bet;

    boolean end = false;


    final HandPanel handPanel = new HandPanel(hand);
    final HandPanel enemypanel = new HandPanel(enemy);


    JPanel buttons = new JPanel();
    UUID clientId;
    JButton hitButton = new JButton("Hit");
    JButton standButton = new JButton("Stand");

    JFrame frame;

    private String nazwa;

    @SneakyThrows
    public UserInterface(ConcurrentLinkedQueue<Event> outbandEvents) {
        this.outbandEvents = outbandEvents;
        Login loginForm = new Login(this);
        loginForm.setVisible(true);  // Ustawienie okna logowania jako nievisible

        nazwa = loginForm.getNazwa();



    }

    public void onLogin(int id, int bet) {
        this.id_gracza = id;
        this.bet = bet;

        frame = new JFrame(nazwa);
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
            buttons.setVisible(false);

        });


        standButton.addActionListener(e -> {
            // Obsługa zdarzenia dla przycisku "Stand"
            sendEvent(new StandEvent(clientId));
            buttons.setVisible(false);
            end = true;


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
        buttons.setVisible(false);

        frame.setVisible(true);
        buttons.setVisible(true);

    }


    @Override
    public void run() {


    }

    public void sendEvent(Event event) {
        this.outbandEvents.add(event);
    }

    @SneakyThrows
    private void update() {
        SwingUtilities.invokeAndWait(() -> {
                    handPanel.updateHand(hand);
                    enemypanel.updateHand(enemy);
                }
        );
    }

    private void checkforpoints(Hand hand) {
        if (hand.calculateCardValue() > 21) {
            buttons.setVisible(false);
            sendEvent(new BustEvent(clientId));
            enemy.show();
        }
    }

    //    private void restart() {
//        this.hand = new Hand();
//        this.enemy = new Hand();
//        update();
//    }
    @SneakyThrows
    public void handleEvent(Event event) {
        log.info(event.toString());
        if (event instanceof EndingRequest) {
            enemy.show();
            sendEvent(new Ending(clientId,hand));

        }
        if (event instanceof WinResultEvent) {
            enemy.show();

            if (!Objects.equals(clientId, ((WinResultEvent) event).getId())) {
                JOptionPane.showMessageDialog(frame, "Wygrałeś pieniądze zostaną wysłane na konto :)", "Gratulacje", JOptionPane.INFORMATION_MESSAGE);
                Connection connection = BazaDanychPolaczenie.connect();
                String query = "SELECT * FROM Gracze WHERE id_gracza = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id_gracza);
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()) {
                    int aktualne = result.getInt("pieniadze");
                    String queryInsert = "INSERT INTO CzarnaLista(nazwa) VALUES(?)";
                    PreparedStatement preparedStatementInsert = connection.prepareStatement(queryInsert);
                    preparedStatementInsert.setString(1, result.getString("nazwa"));
                    preparedStatementInsert.executeUpdate();


                    String queryUpdate = "UPDATE Gracze SET pieniadze = ? WHERE id_gracza = ?";
                    PreparedStatement preparedStatementUpdate = connection.prepareStatement(queryUpdate);
                    preparedStatementUpdate.setInt(1, aktualne + bet * 2);
                    preparedStatementUpdate.setInt(2, id_gracza);
                    preparedStatementUpdate.executeUpdate();
                }
                System.exit(1);
            } else {

                Connection connection = BazaDanychPolaczenie.connect();
                String query = "SELECT * FROM Gracze WHERE id_gracza = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id_gracza);
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()) {
                    int aktualne = result.getInt("pieniadze");
                    if(aktualne<=0){
                        JOptionPane.showMessageDialog(frame, "Przyjdź jak trochę zarobisz usuwamy ci kartę :)", "Koniec", JOptionPane.INFORMATION_MESSAGE);
                        String queryDelete = "DELETE FROM Gracze WHERE id_gracza = ?";
                        PreparedStatement preparedStatementRemove = connection.prepareStatement(queryDelete);
                        preparedStatementRemove.setInt(1, id_gracza);
                        preparedStatementRemove.executeUpdate();
                    }

                    String queryUpdate = "UPDATE Gracze SET pieniadze = ? WHERE id_gracza = ?";
                    PreparedStatement preparedStatementUpdate = connection.prepareStatement(queryUpdate);
                    preparedStatementUpdate.setInt(1, aktualne - bet);
                    preparedStatementUpdate.setInt(2, id_gracza);
                    preparedStatementUpdate.executeUpdate();
                }
                JOptionPane.showMessageDialog(frame, "Przegrałeś pieniądze zostaną zabrane z konta :)", "Koniec", JOptionPane.INFORMATION_MESSAGE);
                System.exit(1);

            }

        }
        if (!end) {


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

            } else if (event instanceof BustEvent) {
                buttons.setVisible(false);
                enemy.show();
                sendEvent(new Ending(clientId, hand));

            }

        }
        update();
        checkforpoints(hand);
    }
}
