public class Card{
    private String kolor;
    private String ranga;
    private String pathToPng;
    public Card(String ranga,String kolor){
        this.ranga = ranga;
        this.kolor = kolor;
        this.pathToPng= "cards/" + ranga.toLowerCase() + "_of_" + kolor.toLowerCase() + ".png";
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