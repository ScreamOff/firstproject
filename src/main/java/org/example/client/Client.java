package org.example.client;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.event.init.Event;

import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Getter
public class Client {
    UserInterface userInterface;
    ClientConnection clientConnection;
    String host;
    int port;


    @SneakyThrows
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        var outbandEvents = new ConcurrentLinkedQueue<Event>(); //to są te które wychodzą


        this.userInterface = new UserInterface(outbandEvents);

        this.clientConnection = new ClientConnection(host ,port,this.userInterface::handleEvent, outbandEvents);

        this.clientConnection.start();
        this.userInterface.start();

    }


}

