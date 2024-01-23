package org.example.server;

import lombok.extern.slf4j.Slf4j;
import org.example.event.init.Event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.function.BiConsumer;

@Slf4j
/// Klasa pokazująca połączenie serwera dziedzicząca po Thread
public class Connection extends Thread {
    /// Identyfikator połączenia
    private final UUID id;
    /// Gniazdo serwera
    private final ServerSocket serverSocket;
    /// Kolejka zdarzeń do wysłania klientowi
    private final BlockingQueue<Event> queue;
    /// Obsługa zdarzeń
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
        /// Informacja o rozpoczęciu wątku
        log.info("Start thread: " + getName());

        try {
            Socket clientSocket;
            synchronized (serverSocket) {
                /// Akceptacja połączenia od klienta
                clientSocket = serverSocket.accept();
            }

            var out = new ObjectOutputStream(clientSocket.getOutputStream()); /// Strumień wyjściowy
            var in = new ObjectInputStream(clientSocket.getInputStream());   /// Strumień wejściowy

            new Thread(() -> {
                while (true) {
                    try {
                        /// Pobranie zdarzenia z kolejki
                        Event currentEvent = queue.take();
                        /// Wysłanie zdarzenia do klienta
                        out.writeObject(currentEvent);
                        out.flush();
                    } catch (Exception e) {
                        /// Obsługa błędu
                        log.error("Exception: {}", e, e);
                        return;
                    }
                }
            }).start();

            new Thread(() -> {
                while (true) {
                    Event incomingEvent = null;
                    try {
                        /// Odczytanie zdarzenia z klienta
                        incomingEvent = (Event) in.readObject();
                        if (incomingEvent != null) {
                            /// Obsługa otrzymanego zdarzenia
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
            /// Informacja o zamknięcu połączenia
            log.error("Connection closed");
            System.exit(3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
