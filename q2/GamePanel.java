package q2;
/*
 * Yoav Tulpan - yoavtp@gmail.com
 * 19.12.2019
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * a JPanel subclass containing the sudoku board panel and the panel for the
 * buttons set and clear. creates new instances of these classes and adds them
 * to itself
 */
public class GamePanel extends JPanel {

    private final JButton set, clear;
    private final JPanel buttons;
    private final SudokuPanel myPanel;
    private boolean alreadySet = false;

    // no argument constructor, this creates and adds the necessary classes
    public GamePanel() {
        // creating buttons
        buttons = new JPanel();
        set = new JButton("set");
        clear = new JButton("clear");
        buttons.add(set);
        buttons.add(clear);
        // registring the listener to the buttons
        Listener lis = new Listener();
        clear.addActionListener(lis);
        set.addActionListener(lis);
        // creting a sudoku panel
        myPanel = new SudokuPanel();
        // adding everything to itself in the correct layout
        this.setLayout(new BorderLayout());
        this.add(buttons, BorderLayout.SOUTH);
        this.add(myPanel, BorderLayout.CENTER);
    }

    // internal listener class for the set and clear buttons

    private class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == clear) {
                myPanel.resetBoard();
                alreadySet = false;
            } else if (event.getSource() == set) {
                if (!alreadySet) {
                    myPanel.setPermanent();
                    alreadySet = true;
                }
            }
            revalidate();
            repaint();
        }
    }
}
