package org.example.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Setter
@Getter
public class LoseEvent implements Event {
    private final String s = "Przegrales";

    private void consume() {

    }

}
