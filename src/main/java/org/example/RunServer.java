package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.server.Server;

@Slf4j
/// Klasa obsługująca serwer
public class RunServer {
    public static void main(String[] args) {
        /// Port, na którym będzie działać serwer
        int port = 12224;

        /// Tworzenie nowego obiektu serwera i uruchamianie go z limitem maksymalnej liczby klientów ustawionym na 2
        new Server(port, 2);
    }
}
