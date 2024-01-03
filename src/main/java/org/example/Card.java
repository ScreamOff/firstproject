package org.example;

public class Card{
    private String kolor;
    private String ranga;
    private String pathToPng;
    static private String pathToBack="src/cards/back.png";
    public Card(String ranga,String kolor){
        this.ranga = ranga;
        this.kolor = kolor;
        if (ranga.equals("Jack") || ranga.equals("Queen")||ranga.equals("King")||ranga.equals("Ace")){
            this.pathToPng= "src/cards/" + ranga.toLowerCase() + "_of_" + kolor.toLowerCase() + ".png";

        }else{
            switch(Integer.parseInt(ranga)){
                case 2:
                    this.pathToPng= "src/cards/two_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 3:
                    this.pathToPng= "src/cards/three_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 4:
                    this.pathToPng= "src/cards/four_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 5:
                    this.pathToPng= "src/cards/five_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 6:
                    this.pathToPng= "src/cards/six_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 7:
                    this.pathToPng= "src/cards/seven_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 8:
                    this.pathToPng= "src/cards/eight_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 9:
                    this.pathToPng= "src/cards/nine_of_" + kolor.toLowerCase() + ".png";
                    break;
                case 10:
                    this.pathToPng= "src/cards/ten_of_" + kolor.toLowerCase() + ".png";
                    break;


            }}
    }
    public String getRanga(){
        return ranga;
    }
    public String getKolor(){
        return kolor;
    }
    public String getPathToPng(){
        return pathToPng;
    }
    @Override
    public String toString(){
        return ranga.toLowerCase() + "_of_" + kolor.toLowerCase();
    }

}
