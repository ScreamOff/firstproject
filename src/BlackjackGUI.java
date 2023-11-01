import javax.swing.*;
import java.awt.*;

public class BlackjackGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Zielone Okno");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(50, 120, 60));

        frame.setSize(400, 300); // Rozmiar okna
        frame.setVisible(true);
    }
}

