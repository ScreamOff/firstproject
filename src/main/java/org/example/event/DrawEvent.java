package org.example.event;

import org.example.base.Card;
import org.example.event.init.Event;
import org.example.server.ServerClient;

import java.util.List;

/// Klasa reprezentująca zdarzenie pobrania karty w grze.
public class DrawEvent implements Event {

    /// Metoda do obsługi zdarzenia pobrania karty.
    /// Wysyła każdemu klientowi (ServerClient) z listy klientów (clientList) zdarzenie CardEvent z przekazaną kartą.
    public void consume(List<ServerClient> clientList, Card card) {
        clientList.forEach(client -> client.sendEvent(new CardEvent(client.getId(), card)));
    }
}
