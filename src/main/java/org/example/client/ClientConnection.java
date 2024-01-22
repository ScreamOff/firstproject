package org.example.client;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.event.Event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

@AllArgsConstructor
@Slf4j
public class ClientConnection extends Thread {
    String host;
    int port;
    private Consumer<Event> eventHandler;
    private ConcurrentLinkedQueue<Event> outbandEvents;

    @SneakyThrows
    @Override
    public void run() {

        try {
            Socket clientSocket = new Socket(host, port);

            var out = new ObjectOutputStream(clientSocket.getOutputStream()); // If both sides first construct the ObjectInputStream, both will block trying to read the object stream header, which won't be written until the ObjectOutputStream has been created (on the other side of the line); which will never happen because both sides are blocked in the constructor of ObjectInputStream
            var in = new ObjectInputStream(clientSocket.getInputStream());

            new Thread(() -> {
                while (true) {
                    var currentEvent = outbandEvents.poll();
                    if (currentEvent != null) {
                        System.out.println("Send object: {}" + currentEvent);
                        try {
                            out.writeObject(currentEvent);
                            out.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }).start();

            new Thread(() -> {
                while (true) {
                    Event incomingEvent = null;
                    try {
                        incomingEvent = (Event) in.readObject();
                        if (incomingEvent != null) {
                            log.info("New incoming event: {}", incomingEvent);
                            eventHandler.accept(incomingEvent);
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
