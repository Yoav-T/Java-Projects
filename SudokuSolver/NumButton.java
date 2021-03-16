package q2;
/*
 * Yoav Tulpan - yoavtp@gmail.com
 * 19.12.2019
 */

import javax.swing.JButton;

/**
 * subclass of JButton that has fields for the button's location in the sudoku
 * board this is so that when we get the button press event, we can know the
 * button's location inside the game board
 */
public class NumButton extends JButton {

    private final int row;
    private final int col;

    // constructor with regular String argument, and additional coordinate integer arguments
    public NumButton(String text, int row, int col) {
        super(text);
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }
}
