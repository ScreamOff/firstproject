package org.example.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.event.Event;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiConsumer;

@Slf4j
@Getter
public class ServerClient {
    private final UUID id;
    private final BlockingQueue<Event> events;
    private final BiConsumer<UUID, Event> eventHandler;
    private boolean ready = false;


    public ServerClient(BiConsumer<UUID, Event> eventHandler) {
        this.id = UUID.randomUUID();
        this.events = new LinkedBlockingQueue<>();
        this.eventHandler = eventHandler;
    }

    public void sendEvent(Event event) {
        log.info(event.toString());
        this.events.add(event);
    }

    public void markAsReady() {
        this.ready = true;
    }
}
