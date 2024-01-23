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
/// Klasa przedstawaijąca interfejs całej gry
public class UserInterface extends Thread {

    /// Kolejka zdarzeń wychodzących.
    private ConcurrentLinkedQueue<Event> outbandEvents;

    /// Ręka gracza.
    private Hand hand = new Hand();

    /// Ręka przeciwnika.
    private Hand enemy = new Hand();

    /// Identyfikator gracza.
    private int id_gracza, bet;

    /// Flaga sygnalizująca zakończenie rozgrywki.
    private boolean end = false;

    /// Panel z kartami gracza.
    private final HandPanel handPanel = new HandPanel(hand);

    /// Panel z kartami przeciwnika.
    private final HandPanel enemyPanel = new HandPanel(enemy);

    /// Panel przycisków.
    private JPanel buttons = new JPanel();

    /// Identyfikator klienta.
    private UUID clientId;

    /// Przycisk "Hit" (dobierz kartę).
    private JButton hitButton = new JButton("Hit");

    /// Przycisk "Stand" (zostań przy obecnych kartach).
    private JButton standButton = new JButton("Stand");

    /// Referencja do głównego okna interfejsu.
    private JFrame frame;

    /// Nazwa gracza.
    private String nazwa;

    /// Konstruktor klasy Interfejsu gracza.
    /// @param outbandEvents Kolejka zdarzeń wychodzących.
    @SneakyThrows
    public UserInterface(ConcurrentLinkedQueue<Event> outbandEvents) {
        this.outbandEvents = outbandEvents;
        Login loginForm = new Login(this);
        loginForm.setVisible(true);  // Ustawienie okna logowania jako niewidoczne
        nazwa = loginForm.getNazwa();
    }

    /// Metoda obsługująca zdarzenie logowania.
    /// @param id Identyfikator gracza.
    /// @param bet Zakład gracza.
    public void onLogin(int id, int bet) {
        this.id_gracza = id;
        this.bet = bet;

        frame = new JFrame(nazwa);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(50, 120, 60));
        frame.setLayout(new BorderLayout());
        frame.setSize(1000, 1000);

        /// Konfiguracja przycisków
        int rows = 5, cols = 6;
        buttons.setLayout(new GridLayout(rows, cols));
        buttons.setOpaque(false);

        /// Dodanie przycisków
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

        /// Obsługa zdarzenia dla przycisku "Hit"
        hitButton.addActionListener(e -> {
            sendEvent(new HitEvent(clientId));
            buttons.setVisible(false);
        });

        /// Obsługa zdarzenia dla przycisku "Stand"
        standButton.addActionListener(e -> {
            sendEvent(new StandEvent(clientId));
            buttons.setVisible(false);
            end = true;
        });

        hitButton.setVisible(true);
        standButton.setVisible(true);

        /// Utworzenie panelu kart
        JPanel cards = new JPanel();
        cards.setLayout(new GridLayout(2, 1));

        /// Dodanie przycisk do okna
        frame.add(buttons, BorderLayout.SOUTH);
        /// Dodanie panelu reki gracza do okna
        frame.add(handPanel, BorderLayout.CENTER);
        /// Dodanie panelu reki przeciwnego gracza do okna
        frame.add(enemyPanel, BorderLayout.NORTH);

        ////Aktualizacja widoku paneli kart gracza
        handPanel.updateHand(hand);
        ////Aktualizacja widoku paneli kart gracza przeciwnego
        enemyPanel.updateHand(enemy);

        buttons.setVisible(false);

        frame.setVisible(true);
        buttons.setVisible(true);
    }

    /// Metoda wysyłająca zdarzenie do kolejki zdarzeń wychodzących.
    /// @param event Zdarzenie do wysłania.
    public void sendEvent(Event event) {
        this.outbandEvents.add(event);
    }

    /// Metoda obsługująca zdarzenia w interfejsie użytkownika.
    @SneakyThrows
    public void handleEvent(Event event) {
        log.info(event.toString());

        /// Obsługa różnych typów zdarzeń
        if (event instanceof EndingRequest) {
            enemy.show();
            sendEvent(new Ending(clientId, hand));
        } else if (event instanceof WinResultEvent) {
            enemy.show();

            if (!Objects.equals(clientId, ((WinResultEvent) event).getId())) {
                // Obsługa zwycięstwa
                JOptionPane.showMessageDialog(frame, "Wygrałeś! Pieniądze zostaną wysłane na konto :)", "Gratulacje", JOptionPane.INFORMATION_MESSAGE);

                /// Aktualizacja stanu konta gracza
                Connection connection = BazaDanychPolaczenie.connect();
                String query = "SELECT * FROM Gracze WHERE id_gracza = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id_gracza);
                ResultSet result = preparedStatement.executeQuery();

                if (result.next()) {
                    int aktualne = result.getInt("pieniadze");

                    /// Dodanie do czarnej listy
                    String queryInsert = "INSERT INTO CzarnaLista(nazwa) VALUES(?)";
                    PreparedStatement preparedStatementInsert = connection.prepareStatement(queryInsert);
                    preparedStatementInsert.setString(1, result.getString("nazwa"));
                    preparedStatementInsert.executeUpdate();

                    /// Aktualizacja stanu konta po wygranej
                    String queryUpdate = "UPDATE Gracze SET pieniadze = ? WHERE id_gracza = ?";
                    PreparedStatement preparedStatementUpdate = connection.prepareStatement(queryUpdate);
                    preparedStatementUpdate.setInt(1, aktualne + bet * 2);
                    preparedStatementUpdate.setInt(2, id_gracza);
                    preparedStatementUpdate.executeUpdate();
                }

                // Zakończenie programu
                System.exit(1);
            } else {
                /// Obsługa przegranej
                Connection connection = BazaDanychPolaczenie.connect();
                String query = "SELECT * FROM Gracze WHERE id_gracza = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id_gracza);
                ResultSet result = preparedStatement.executeQuery();

                if (result.next()) {
                    int aktualne = result.getInt("pieniadze");

                    /// Usunięcie z konta po przegranej
                    String queryUpdate = "UPDATE Gracze SET pieniadze = ? WHERE id_gracza = ?";
                    PreparedStatement preparedStatementUpdate = connection.prepareStatement(queryUpdate);
                    preparedStatementUpdate.setInt(1, aktualne - bet);
                    preparedStatementUpdate.setInt(2, id_gracza);
                    preparedStatementUpdate.executeUpdate();

                    /// Komunikat o przegranej
                    JOptionPane.showMessageDialog(frame, "Przegrałeś! Pieniądze zostaną zabrane z konta :)", "Koniec", JOptionPane.INFORMATION_MESSAGE);

                    // Zakończenie programu
                    System.exit(1);
                }
            }
        } else if (!end) {
            /// Obsługa różnych zdarzeń w trakcie rozgrywki

            if (event instanceof ConnectionAcceptEvent) {
                /// Zdarzenie akceptacji połączenia
                this.clientId = ((ConnectionAcceptEvent) event).getId();
                this.sendEvent(new ReadyEvent(this.clientId));
            } else if (event instanceof CardEvent) {
                /// Zdarzenie otrzymania karty
                ((CardEvent) event).consume(clientId, hand, enemy);

                /// Blokowanie karty przeciwnika po otrzymaniu jednej karty
                if (enemy.size() == 1) {
                    enemy.block();
                }

                /// Sprawdzenie punktów na ręce gracza
                checkForPoints(hand);
            } else if (event instanceof PingEvent) {
                /// Zdarzenie ping (odblokowanie przycisków)
                buttons.setVisible(true);
            } else if (event instanceof BustEvent) {
                /// Zdarzenie przekroczenia liczby punktów (Bust)
                buttons.setVisible(false);
                enemy.show();
                sendEvent(new Ending(clientId, hand));
            }
        }

        /// Aktualizacja widoku
        update();
        checkForPoints(hand);
    }

    /// Metoda aktualizująca widok interfejsu użytkownika.
    @SneakyThrows
    private void update() {
        SwingUtilities.invokeAndWait(() -> {
            handPanel.updateHand(hand);
            enemyPanel.updateHand(enemy);
        });
    }

    /// Metoda sprawdzająca liczbę punktów na ręce gracza i przeciwnika.
    /// @param hand Ręka do sprawdzenia.
    private void checkForPoints(Hand hand) {
        if (hand.calculateCardValue() > 21) {
            buttons.setVisible(false);
            sendEvent(new BustEvent(clientId));
            enemy.show();
        }
    }
}
