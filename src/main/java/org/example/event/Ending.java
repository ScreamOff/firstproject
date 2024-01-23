package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.base.Hand;
import org.example.event.init.Event;
import org.example.server.ScoreBoard;
import org.example.server.ScoreBoardComparator;
import org.example.server.ServerClient;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Ending implements Event {
    private UUID id;
    private Hand hand;

    public void consume(List<ScoreBoard> sc,List<ServerClient> clientList) {
        sc.add(new ScoreBoard(this.hand.calculateCardValue(), this.id));
        if(sc.size()==2){
            sc.sort(new ScoreBoardComparator());
            clientList.forEach(client -> {
                client.sendEvent(new WinResultEvent(sc.getFirst().getId()));
            });
        }

    }

}
