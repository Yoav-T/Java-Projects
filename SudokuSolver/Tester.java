package q2;
/*
 * Yoav Tulpan - yoavtp@gmail.com
 * 19.12.2019
 */

import javax.swing.*;

// simple main tester class to operate the game module
public class Tester {

    public static void main(String args[]) {
        JFrame frame = new JFrame("Sudoku Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        GamePanel p = new GamePanel();

        frame.add(p);
        frame.setVisible(true);
    }
}
