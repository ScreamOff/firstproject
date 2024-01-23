package org.example.client;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.event.init.Event;

import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Getter
/// Klasa przedstawaijąca klienta
public class Client {

    /// Interfejs użytkownika.
    private UserInterface userInterface;

    /// Połączenie klienta z serwerem.
    private ClientConnection clientConnection;

    /// Adres hosta serwera.
    private String host;

    /// Numer portu, na którym nasłuchuje serwer.
    private int port;

    /// Konstruktor klasy Client z argumantami Adres hosta serwera i Numer portu, na którym nasłuchuje serwer.
    @SneakyThrows
    public Client(String host, int port) {
        this.host = host;
        this.port = port;

        // Kolejka zdarzeń wychodzących
        var outboundEvents = new ConcurrentLinkedQueue<Event>();

        // Inicjalizacja interfejsu użytkownika
        this.userInterface = new UserInterface(outboundEvents);

        // Inicjalizacja połączenia z serwerem
        this.clientConnection = new ClientConnection(host, port, this.userInterface::handleEvent, outboundEvents);

        // Uruchomienie wątku obsługującego połączenie z serwerem
        this.clientConnection.start();

        // Uruchomienie wątku obsługującego interfejs użytkownika
        this.userInterface.start();
    }
}
