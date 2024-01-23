package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.client.Client;
import org.example.server.Server;

@Slf4j
/// Klasa do obsługi terminala
public class ForTerminalUse {
    public static void main(String[] args) {
        /// Logowanie informacji "asd"
        log.info("asd");

        /// Sprawdzenie, czy liczba argumentów jest różna od 1
        if (args.length != 1) {
            /// Logowanie błędu i rzucenie wyjątku IllegalArgumentException w przypadku nieprawidłowej liczby argumentów
            log.error("Provide one of arguments: --client or --server");
            throw new IllegalArgumentException("No mode args");
        }

        /// Port, na którym będą działać klient lub serwer
        int port = 12224;

        /// Sprawdzenie, czy pierwszy argument to "--client" (ignorując wielkość liter)
        if ("--client".equalsIgnoreCase(args[0])) {
            new Client("localhost", port);
        } else if ("--server".equalsIgnoreCase(args[0])) {
            new Server(port, 2);
        }
    }
}
