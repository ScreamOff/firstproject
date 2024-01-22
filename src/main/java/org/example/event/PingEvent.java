package org.example.event;

public class PingEvent implements Event {
    public void consume() {
        System.out.println("PING");
    }
}
