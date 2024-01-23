package org.example.server;

import lombok.extern.slf4j.Slf4j;
import org.example.event.init.ConnectionAcceptEvent;

import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class Server {
    private final List<ServerClient> clients;

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
            //System.out.println("Start connection: " + i);

            var client = new ServerClient(game::handleEvent);

            client.sendEvent(new ConnectionAcceptEvent(client.getId()));
            clients.add(client);
            new Connection(client.getId(), serverSocket, client.getEvents(), client.getEventHandler())
                    .start();
        }


    }
}
