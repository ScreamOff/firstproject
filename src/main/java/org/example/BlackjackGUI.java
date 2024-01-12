package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
public class BlackjackGUI {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 9876;
    private Player player;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Socket socket;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI(new Player(new Deck()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void initializeStreams() {
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(Object object) {
        try {
            outputStream.writeObject(object);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object receive() {
        try {
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sender() {
        if (player.isStand()) {
            send("Stand");
        }
        if (player.isHit()) {
            send("Hit");
            player.setHit(false);
        }
    }

    private Object receiver(Object o) {
        if (o instanceof Player) {
            this.player = (Player) o;
            return o;
        }
        if (o instanceof Card) {
            player.addCardToHand((Card) o);
            return o;
        }
        if (o instanceof Integer) {
            if ((Integer) o == 1) {
                return 1;
            }
        }
        if (o instanceof Integer) {
            if ((Integer) o == 0) {
                return 0;
            }
        }
        return o;
    }

    private static void createAndShowGUI(Player player) throws IOException {
        JFrame frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(50, 120, 60));

        PlayerPanel panel = new PlayerPanel(player);
        panel.updateHand();
        panel.updateScore();
        frame.setLayout(new BorderLayout());
        frame.add(panel.getPlayerPanel());
        frame.setSize(1000, 1000);

        frame.setVisible(true);
    }
}
