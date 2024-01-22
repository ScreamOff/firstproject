package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Getter
@AllArgsConstructor
@Setter
public class HitEvent implements Event {
    UUID id;

    public void consume() {

    }
}
