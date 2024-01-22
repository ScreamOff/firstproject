package org.example.server;

import java.util.Comparator;

public class ScoreBoardComparator implements Comparator<ScoreBoard> {
    @Override
    public int compare(ScoreBoard o1, ScoreBoard o2) {
        // Porównaj obiekty ScoreBoard na podstawie ilości punktów (malejąco)
        return Integer.compare(o2.getPoints(), o1.getPoints());
    }
}
