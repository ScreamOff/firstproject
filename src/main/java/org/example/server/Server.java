package org.example.server;

import lombok.extern.slf4j.Slf4j;
import org.example.event.init.ConnectionAcceptEvent;

import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;

@Slf4j
/// Klasa serwera obsługującego klientów.
public class Server {
    /// Lista klientów podłączonych do serwera.
    private final List<ServerClient> clients;

    /// Konstruktor klasy Server.
    /// @param port Numer portu, na którym ma nasłuchiwać serwer.
    /// @param connectionNumber Liczba połączeń, które serwer ma obsługiwać.
    public Server(int port, int connectionNumber) {
        this.clients = new LinkedList<>();
        var game = new Game(clients);

        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            throw new RuntimeException("Could not create ServerSocket ", e);
        }

        for (int i = 0; i < connectionNumber; i++) {
            //log.info("Start connection: {}", i);
            // System.out.println("Start connection: " + i);

            var client = new ServerClient(game::handleEvent);

            client.sendEvent(new ConnectionAcceptEvent(client.getId()));
            clients.add(client);
            new Connection(client.getId(), serverSocket, client.getEvents(), client.getEventHandler())
                    .start();
        }
    }
}
