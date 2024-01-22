package org.example;

import javax.swing.*;
import java.awt.*;
public class BorderLayoutExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("BorderLayout Example");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // create a border layout
        // add buttons to the panel in different regions
        panel.add(new JButton("North"), BorderLayout.NORTH);
        panel.add(new JButton("South"), BorderLayout.SOUTH);
        //panel.add(new JButton("West"), BorderLayout.WEST);
        //panel.add(new JButton("East"), BorderLayout.EAST);
        panel.add(new JButton("Center"), BorderLayout.CENTER);
        frame.add(panel); // add the panel to the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); // adjust the size of the frame to fit the components
        frame.setVisible(true); // show the frame
    }
}