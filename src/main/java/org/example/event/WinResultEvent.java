package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.event.init.Event;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class WinResultEvent implements Event {
    private UUID id;


}
