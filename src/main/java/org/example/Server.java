package org.example;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.io.*;
public class Server {


    private static final int PORT = 9876;
    private Player[] clients = new Player[2];
    private int[] pasKlientow = new int[2];
    private int tura;
    Deck deck = new Deck();
    Random random = new Random();
    private void waitForClients() throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);
        for(int i =0;i<2;i++){

            Socket clientSocket = serverSocket.accept();
            System.out.println("Klient dołączył : "+(i+1));
            clients[i] = new Player(deck);
            i++;
            }

        }
    /*private void obsluga() throws IOException {
        tura = random.nextInt(2);
        if(receive == "Hit"){

            Card karta = deck.drawCard();
            ssend(karta);
            clients[tura].addCardToHand(karta);
            if(tura == 0 && pasKlientow[tura]==0){
                tura++;
            }
            if(tura == 1 && pasKlientow[tura]==0){
                tura--;
            }

        }
    }*/

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.waitForClients();
        //server.obsluga();

    }
}


