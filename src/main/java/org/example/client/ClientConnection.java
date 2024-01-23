package org.example.client;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.event.init.Event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

@AllArgsConstructor
@Slf4j
/// Klasa przedstawaijąca połączenie klienta
public class ClientConnection extends Thread {

    /// Adres hosta serwera.
    private String host;

    /// Numer portu, na którym nasłuchuje serwer.
    private int port;

    /// Funkcja obsługująca zdarzenia przychodzące od serwera.
    private Consumer<Event> eventHandler;

    /// Kolejka zdarzeń do wysłania na serwer.
    private ConcurrentLinkedQueue<Event> outboundEvents;

    @SneakyThrows
    @Override
    public void run() {
        try {
            // Tworzenie gniazda klienta i strumieni do komunikacji z serwerem
            Socket clientSocket = new Socket(host, port);
            var out = new ObjectOutputStream(clientSocket.getOutputStream());
            var in = new ObjectInputStream(clientSocket.getInputStream());

            /// Wątek obsługujący wysyłanie zdarzeń do serwera
            new Thread(() -> {
                while (true) {
                    var currentEvent = outboundEvents.poll();
                    if (currentEvent != null) {
                        log.info("Sending object: {}", currentEvent);
                        try {
                            out.writeObject(currentEvent);
                            out.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }).start();

            /// Wątek obsługujący odbieranie zdarzeń od serwera
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
