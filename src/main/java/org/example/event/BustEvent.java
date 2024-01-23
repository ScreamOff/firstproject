package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.event.init.Event;
import org.example.server.ServerClient;

import java.util.List;
import java.util.UUID;
@Getter
@AllArgsConstructor
public class BustEvent implements Event {
    UUID id;
    public void consume(List<ServerClient> clientList){
        clientList.forEach(client -> {
            client.sendEvent(new WinResultEvent(id));
        });

    }

}
