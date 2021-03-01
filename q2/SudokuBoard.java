package q2;
/*
 * Yoav Tulpan - yoavtp@gmail.com
 * 19.12.2019
 */

/**
 * logic class for the sudoku board. this is where the data is saved and move
 * legality tested numbers are saved in a SIZE*SIZE integer array, permanent
 * (set) status is saved in a SIZE*SIZE boolean array. legality of a move is
 * checked against block, row, col and if the location has already been set with
 * a permanent number.
 */
public class SudokuBoard {

    private final int[][] board;
    private final boolean[][] permNum;
    // modular board size: must be a perfect square (4, 9, 16, ...) otherwise
    // the game's rules cannot be maintained. try 4 or 16 for fun (or 25 if you have a big monitor)
    private final int SIZE = 9;
    private final int BLOCK_SIZE = (int) Math.sqrt(SIZE);

    // simple no-argument constructor that creates the data arrays
    public SudokuBoard() {
        board = new int[SIZE][SIZE];
        permNum = new boolean[SIZE][SIZE];
    }

    /**
     *
     * @param val = the value entered into the button/cell
     * @param row = the row in which the cell is located
     * @param col = the column in which the cell is located
     * @return if this move violates the rules (number already in col or row or
     * block, cell already set permanently) return false. otherwise true.
     */
    public boolean legalMove(int val, int row, int col) {
        int rowBlock = (int) (row / BLOCK_SIZE); // which block is this move in
        int colBlock = (int) (col / BLOCK_SIZE);
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == val // value's row 
                    || board[i][col] == val // value's column check
                    || board[BLOCK_SIZE * rowBlock + i % BLOCK_SIZE][BLOCK_SIZE * colBlock + ((int) (i / BLOCK_SIZE) % BLOCK_SIZE)] == val
                    // value's block check
                    || getPerm(row, col)) // location has been set permanently
            {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param val = received value from the interactive SudokuPanel
     * @param row = row location of entry
     * @param col = col location of entry enters the new value into the number
     * board data
     */
    public void setNum(int val, int row, int col) {
        this.board[row][col] = val;
    }

    /**
     * if button "set" has been pressed in SudokuPanel, the panel iterates over
     * the board and finds the cells with numbers already assigned. it paints
     * them red and sets them as permanent using this setPerm method.
     *
     * @param row = row of cell to be set permanent
     * @param col = col of cell to be set permanent
     */
    public void setPerm(int row, int col) {
        this.permNum[row][col] = true;
    }

    /**
     * used by SudokuPanel to check a cell's number
     *
     * @param row = requested row
     * @param col = requested column
     * @return number in the specified cell
     */
    public int getNum(int row, int col) {
        return board[row][col];
    }

    /**
     * getter for a cell's permanent status
     *
     * @param row
     * @param col
     * @return true if cell is already permanent, false if it isn't.
     */
    public boolean getPerm(int row, int col) {
        return permNum[row][col];
    }
    // size getter
    public int getSize() {
        return this.SIZE;
    }
    // blocksize getter
    public int getBlockSize() {
        return this.BLOCK_SIZE;
    }

    /**
     * if SudokuPanel 'clear' button has been pressed, we reset all the values
     * in our data to 0 in the numbers array, and false in the boolean permanent
     * array like it was in the default.
     */
    public void resetBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 0;
                permNum[i][j] = false;
            }
        }
    }
}
