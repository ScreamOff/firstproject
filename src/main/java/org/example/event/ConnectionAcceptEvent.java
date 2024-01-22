package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ConnectionAcceptEvent implements Event {
    private UUID id;

    void consume() {

    }

    @Override
    public String toString() {
        return "ConnectionAcceptEvent{id=" + getId() + "}";
    }
}
