package org.example.server;

import lombok.extern.slf4j.Slf4j;
import org.example.event.Event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.function.BiConsumer;

@Slf4j
public class Connection extends Thread {
    private final UUID id;
    private final ServerSocket serverSocket;
    private final BlockingQueue<Event> queue;
    private final BiConsumer<UUID, Event> eventHandler;

    public Connection(UUID id, ServerSocket serverSocket, BlockingQueue<Event> queue, BiConsumer<UUID, Event> eventHandler) {
        this.id = id;
        this.serverSocket = serverSocket;
        this.queue = queue;
        this.eventHandler = eventHandler;

        setName("thread_" + id);
    }


    @Override
    public void run() {

        log.info("Start thread:" + getName());
        try {
            Socket clientSocket;
            log.info("asd");
            synchronized (serverSocket) {
                clientSocket = serverSocket.accept();
            }
            log.info("asd1");

            var out = new ObjectOutputStream(clientSocket.getOutputStream());
            var in = new ObjectInputStream(clientSocket.getInputStream());
            log.info("asd2");

            new Thread(() -> {
                while (true) {
                    try {
                        Event currentEvent = queue.take();
                        System.out.println("Send object: {}" + currentEvent);
                        out.writeObject(currentEvent);
                        out.flush();
                    } catch (Exception e) {
                        log.error("Exception: {}", e, e);
                        return;
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
                            eventHandler.accept(id, incomingEvent);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();

        } catch (IOException e) {
            log.error("Connection closed");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
