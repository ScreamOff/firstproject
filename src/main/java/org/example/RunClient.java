package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.client.Client;

@Slf4j
///Klasa obsługująca klienta
public class RunClient {
    public static void main(String[] args) {
        /// Port, na którym będzie działać klient
        int port = 12224;

        /// Tworzenie nowego obiektu klienta i uruchamianie go
        new Client("localhost", port);
    }
}
