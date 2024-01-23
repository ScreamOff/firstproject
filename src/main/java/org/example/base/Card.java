package org.example.base;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Getter
@Setter
/// klasa przedstawiajaca karty
public class Card implements Serializable {
    /// Zmienna ze ścieżką do pliku z tyłem karty
    private final String pathToBack = "src/cards/back.png";
    /// Zmienna na kolor karty
    private String kolor;
    /// Zmienna na range karty (np. "Hearts", "Diamonds", "Clubs", "Spades")
    private String ranga;
    /// Zmienna ze ścieżką do pliku z obrazem karty
    private String pathToPng;
    /// Flaga określająca, czy karta jest ograniczona w użyciu
    private boolean restricted = false;
    /// Konstruktor klasy przyjmujący range i kolor karty
    public Card(String ranga, String kolor) {
        this.ranga = ranga;
        this.kolor = kolor;
        /// Ustawienie ścieżki do pliku z obrazem karty w zależności od rangi i koloru
        if (ranga.equals("Jack") || ranga.equals("Queen") || ranga.equals("King") || ranga.equals("Ace")) {
            this.pathToPng = "src/cards/" + ranga.toLowerCase() + "_of_" + kolor.toLowerCase() + ".png";

        } else {
            /// Ustawienie ścieżki do pliku dla kart o wartościach numerycznych
            switch (Integer.parseInt(ranga)) {
                case 2:
                    this.pathToPng = "src/cards/two_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 3:
                    this.pathToPng = "src/cards/three_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 4:
                    this.pathToPng = "src/cards/four_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 5:
                    this.pathToPng = "src/cards/five_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 6:
                    this.pathToPng = "src/cards/six_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 7:
                    this.pathToPng = "src/cards/seven_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 8:
                    this.pathToPng = "src/cards/eight_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 9:
                    this.pathToPng = "src/cards/nine_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 10:
                    this.pathToPng = "src/cards/ten_of_" + kolor.toLowerCase() + ".png";
                    break;


            }
        }
    }


    @Override
    public String toString() {
        return ranga.toLowerCase() + "_of_" + kolor.toLowerCase();
    }


}
