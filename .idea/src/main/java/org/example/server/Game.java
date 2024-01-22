package org.example.server;

import lombok.extern.slf4j.Slf4j;
import org.example.base.Deck;
import org.example.event.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class Game {
    private final List<ServerClient> clientList;
    private final Deck deck;


    private final UUID[] standlist = new UUID[2];

    private final List<ScoreBoard> scoreboard = new ArrayList<ScoreBoard>();


    public Game(List<ServerClient> clientList) {
        this.clientList = clientList;
        deck = new Deck();
    }


    void startGame() {
        this.clientList.forEach(serverClient -> {
            var c1 = new CardEvent(serverClient.getId(), deck.drawCard(), true);
            var c2 = new CardEvent(serverClient.getId(), deck.drawCard(), false);
            this.handleEvent(serverClient.getId(), c1);
            this.handleEvent(serverClient.getId(), c2);
        });
    }

    public void handleEvent(UUID clientId, Event event) {
        log.info("Handle event from client: {}, event: {}", clientId, event);
        if (event instanceof DrawEvent) {
            ((DrawEvent) event).consume(clientList, deck.drawCard());
        } else if (event instanceof ReadyEvent) {
            boolean allReady = ((ReadyEvent) event).consume(clientList);
            if (allReady) {
                startGame();
            }
        } else if (event instanceof CardEvent) {
            //zapisze ze gracz w rece
            this.clientList.forEach(serverClient -> serverClient.sendEvent(event));
        } else if (event instanceof HitEvent) {
            for (ServerClient client : clientList) {
                if (client.getId() != ((HitEvent) event).getId()) {
                    client.sendEvent(new PingEvent());
                }
            }
            this.clientList.forEach(serverClient -> serverClient.sendEvent(new CardEvent(((HitEvent) event).getId(), deck.drawCard(), false)));
        } else if (event instanceof StandEvent) {
            if (standlist[0] != null) {
                standlist[0] = ((StandEvent) event).getId();
            } else standlist[1] = ((StandEvent) event).getId();
            for (ServerClient client : clientList) {
                if (client.getId() != ((StandEvent) event).getId()) {
                    client.sendEvent(new PingEvent());
                }
            }
        } else if (event instanceof Ending) {

            ((Ending) event).consume(scoreboard);
            if (scoreboard.size() == 2) {
                scoreboard.sort(new ScoreBoardComparator());
                for (ServerClient client : clientList) {

                    if (scoreboard.getFirst().getId() == client.getId()) {
                        client.sendEvent(new WinResultEvent());
                    } else client.sendEvent(new LoseEvent());
                }
            }

        }
        if (standlist[0] != null && standlist[1] != null) {
            this.clientList.forEach(serverClient -> serverClient.sendEvent(new ResultEvent()));
        }
    }
}

