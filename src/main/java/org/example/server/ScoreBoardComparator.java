package org.example.server;

import java.util.Comparator;

/// Klasa implementująca interfejs Comparator do porównywania obiektów ScoreBoard.
public class ScoreBoardComparator implements Comparator<ScoreBoard> {

    /// Porównuje dwa obiekty ScoreBoard na podstawie ilości punktów (malejąco).
    @Override
    public int compare(ScoreBoard o1, ScoreBoard o2) {
        return Integer.compare(o2.getPoints(), o1.getPoints());
    }
}
