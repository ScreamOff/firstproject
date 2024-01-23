package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.event.init.Event;
import org.example.server.ServerClient;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class StandEvent implements Event {
    private UUID id;
    public void consume(List<ServerClient> clientList){
        clientList.forEach(client -> {
            if(Objects.equals(this.id,client.getId())){
                client.setStand(true);
            }
        });

    }
}
