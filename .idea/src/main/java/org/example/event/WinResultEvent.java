package org.example.event;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WinResultEvent implements Event {
    private final String s = "Wygrana";


}
