import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;

//klasa klienta gry
public class Client {
    public static void main(String[] args){
        String adresSerwera = "localhost";
        int portSerwera = 12345;//rzeczy do socketa by moc grac w sieci lokalnej
        String msg;//na prompty serwera
        Scanner scanner = new Scanner(system.in); //input uzytkownika
        boolean glownapetla = true;//zmienna potrzebna do utrzymania petli gry
        try{
            Socket socket = new Socket(adresSerwera,portSerwera);
            InputStreamReader inputstreamreader = new InputStreamReader(socket.getInputStream());
            BufferedReader in = new BufferedReader(inputstreamreader);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true); //utworzenie polaczenia z serwerem oraz zarzadzanie jego wejsciem/wyjsciem
            while(glownapetla){
                msg = in.readLine();
                if(msg!= null){

                }
            }
        }


    }
}
