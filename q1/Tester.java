package q1;
/*
 * Yoav Tulpan - yoavtp@gmail.com
 * 19.12.2019
 */

import javax.swing.*;

/**
 * main tester class, no logic performed here. only displays the panel classes.
 */
public class Tester {

    public static void main(String args[]) {
        JFrame frame = new JFrame("Graph Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        MainPanel p = new MainPanel();

        frame.add(p);
        frame.setVisible(true);
    }
}
