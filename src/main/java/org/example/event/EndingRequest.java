package org.example.event;

import org.example.event.init.Event;

public class EndingRequest implements Event {
    public void consume() {
        System.out.println("PING");
    }
}
