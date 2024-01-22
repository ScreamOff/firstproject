package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class StandEvent implements Event {
    private UUID id;
}
