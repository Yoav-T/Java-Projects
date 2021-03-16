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
 * Panel for the Sudoku game, keeps all the number buttons, populates the board
 * in the correct layout with the required amount of buttons. listener class.
 * creates a SudokuBoard variable for data and most of the logic.
 */
public class SudokuPanel extends JPanel {

    private final NumButton[][] buttons;
    private final SudokuBoard board;

    /**
     * no-argument constructor, creates and populates the board with SIZE*SIZE
     * buttons using the grid layout.
     */
    public SudokuPanel() {
        this.board = new SudokuBoard();
        this.setLayout(new GridLayout(board.getSize(), board.getSize(), 3, 3)); // SIZE by SIZE board, spacing of 3
        buttons = new NumButton[board.getSize()][board.getSize()];
        this.populateBoard();
        // registering the Listener as the handler for all our button events
        Listener lis = new Listener();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                buttons[i][j].addActionListener(lis);
            }
        }
    }

    /**
     * incase 'clear' button is pressed, this method resets all the cells to
     * default number 0, and default 'permanent' status 'false'. uses SudokuBoard
     * method resetBoard
     */
    public void resetBoard() {
        this.board.resetBoard();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                buttons[i][j].setText("");
                buttons[i][j].setForeground(Color.BLACK);
            }
        }
        revalidate();
        repaint();
    }

    // method that iterates over all the buttons and sets as permanent 
    // the buttons that have been assigned a value by the user (not default 0)
    // uses the SudokuBoard's setPerm method. also paints them red.
    public void setPermanent() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getNum(i, j) != 0) {
                    board.setPerm(i, j);
                    buttons[i][j].setForeground(Color.RED);
                }
            }
        }
        revalidate();
        repaint();
    }

    // method to create SIZE*SIZE buttons and add them to the board, 
    // paint them correctly, register our Listener as their listener class 
    private void populateBoard() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                buttons[i][j] = new NumButton("", i, j);

                if ((j / board.getBlockSize() + i / board.getBlockSize()) % 2 == 0) {
                    buttons[i][j].setBackground(Color.LIGHT_GRAY);
                } else {
                    buttons[i][j].setBackground(Color.WHITE);
                }

                this.add(buttons[i][j]);
            }
        }
    }

    // internal listener class for the sudoku cell buttons, along with move 
    // legality check (using SudokuBoard's legalMove method)
    private class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            NumButton temp = (NumButton) (event.getSource());
            JTextField value = new JTextField(3);
            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("value to enter:"));
            myPanel.add(value);
            // number of options for number insertion depends on the board size
            Integer[] options = new Integer[board.getSize()];
            for (int i = 0; i < board.getSize(); i++) {
                options[i] = i + 1;
            }

            try {
                int num = (Integer) JOptionPane.showInputDialog(null, "Enter number with keyboard or mouse:",
                        "Sudoku Input", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (board.legalMove(num, temp.getRow(), temp.getCol())) {
                    board.setNum(num, temp.getRow(), temp.getCol());
                    temp.setText("" + num);
                } else {
                    JOptionPane.showMessageDialog(null, String.format("Illegal "
                            + "input for cell (%d, %d)", temp.getRow() + 1, temp.getCol() + 1),
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } // in case the user presses cancel
            catch (Exception e) {
                System.err.println("enter a number!");
            }
        }
    }
}
