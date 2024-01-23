package org.example.event.init;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.server.ServerClient;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ReadyEvent implements Event {
    private UUID playerId;

    public boolean consume(List<ServerClient> players) {
        boolean allReady = false;
        for (int i = 0; i < players.size(); i++) {
            if (Objects.equals(players.get(i).getId(), playerId)) {
                players.get(i).markAsReady();
            } else {
                allReady = players.get(i).isReady();
            }
        }
        return allReady;
    }
}
