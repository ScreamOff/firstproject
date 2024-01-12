package org.example;
import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {

    private static final int PORT = 9876;
    private Player[] clients = new Player[2];
    private int[] passCount = new int[2];
    private int turn;
    private Deck deck = new Deck();
    private Random random = new Random();

    private void waitForClients() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        for (int i = 0; i < 2; i++) {

            System.out.println("Client connected: " + (i + 1));
            clients[i] = new Player(deck);
            clients[i].setClientSocket(serverSocket.accept());
        }
    }

    private void dealerTurn() throws IOException {
        turn = (turn + 1) % 2;
        while (true) {
            Card card = deck.drawCard();
            send(card);
            clients[turn].addCardToHand(card);
            if (turn == 0 && passCount[turn] == 0) {
                turn++;
            }
            if (turn == 1 && passCount[turn] == 0) {
                turn--;
            }
            if (clients[turn].calculateHandValue() == 21 && clients[turn].getHand().size() == 2) {
                break;
            }
        }
    }

    private void playerTurn(int playerIndex) throws IOException {
        while (true) {
            String input = (String) receive();
            if (input.equals("Stand")) {
                passCount[playerIndex]++;
                break;
            }
            Card card = deck.drawCard();
            send(card);
            clients[playerIndex].addCardToHand(card);
            if (clients[playerIndex].calculateHandValue() == 21) {
                break;
            }
        }
    }

    private void gameLoop() throws IOException {
        while (true) {
            dealerTurn();
            for (int i = 0; i < 2; i++) {
                playerTurn(i);
            }
            if (clients[0].calculateHandValue() > 21 && clients[1].calculateHandValue() <= 21) {
                System.out.println("Player 1 wins!");
                break;
            } else if (clients[1].calculateHandValue() > 21 && clients[0].calculateHandValue() <= 21) {
                System.out.println("Player 2 wins!");
                break;
            } else if (clients[0].calculateHandValue() > clients[1].calculateHandValue()) {
                System.out.println("Player 1 wins!");
            } else if (clients[1].calculateHandValue() > clients[0].calculateHandValue()) {
                System.out.println("Player 2 wins!");
            }
        }
    }

    private void send(Object object) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(clients[0].getSocket().getOutputStream());
        outputStream.writeObject(object);
        outputStream.flush();
    }

    private Object receive() throws IOException {
        ObjectInputStream inputStream = new ObjectInputStream(clients[0].getSocket().getInputStream());
        try {
            return inputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.waitForClients();
        server.gameLoop();
    }
}