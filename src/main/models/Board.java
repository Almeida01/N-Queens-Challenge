package main.models;

import main.services.Ilayout;

import java.util.*;

import static java.lang.Math.abs;

/**
 * Board class representing a chess board. <br>
 * The context of this board is to represent a chess board where queens will be placed following the constraints of the n-queens puzzle.
 */
public class Board implements Ilayout {
    /**
     * Integer value representing width and height of the board.
     */
    private final int n;

    /**
     * An integer array representing the board.<br>
     * Each index of the array represents a row, where each index value represents the column where a queen is placed.
     */
    private int[] board;

    /**
     * Integer value representing the number of collisions on the board.
     */
    private int numOfCollisions = 0;

    /**
     * Constant integer value representing the number of diagonals in a board. <br>
     * This value is calculated by the formula: {@code 2 * n - 1};
     */
    private static int NUMBER_OF_DIAGONALS;

    /**
     * Integer array representing diagonals with positive slope.
     */
    private int[] frontSlashCollisions;

    /**
     * Integer array representing diagonals with negative slope.
     */
    private int[] backSlashCollisions;

    /**
     * Constructor class
     *
     * @param n Integer value representing width and height of the board.
     */
    public Board(int n) {
        this.n = n;
        fillBoard();
        NUMBER_OF_DIAGONALS = 2 * n - 1;
        numOfCollisions = collides();
        updatedDiagonalCollisions();
    }

    /**
     * Private constructor class. <br>
     * Only called to create boards from other previous boards.
     *
     * @param n               Integer value representing width and height of the board.
     * @param board           A board is passed as a param in order to facilitate board creation. <br>
     *                        This board is generated equal to the parent board. <br>
     *                        For more information on how this board is modified prior to generation, check {@link #children()}. <br>
     *                        For more information on what a board represents, check {@link #board}.
     * @param numOfCollisions Integer value representing number of collisions. <br>
     *                        For more information on why this value is passed as a parameter, check {@link #children()}.
     */
    private Board(int n, int[] board, int numOfCollisions) {
        this.n = n;
        this.board = board;
        NUMBER_OF_DIAGONALS = 2 * n - 1;
        this.numOfCollisions = numOfCollisions;
    }

    /**
     * Method to initialize and fill a board. <br>
     * This method of initializing a board fills with various diagonals. <br>
     * For a board of size {@code n = 6}: <br>
     * {@code - Q - - - -} <br>
     * {@code - - - Q - -} <br>
     * {@code - - - - - Q} <br>
     * {@code Q - - - - -} <br>
     * {@code - - Q - - -} <br>
     * {@code - - - - Q -} <br>
     */
    private void fillBoard() {
        this.board = new int[n];
        int j = 1;
        for (int i = 0; i < n; i++) {
            if (j >= n) j = 0;
            board[i] = j;
            j += 2;
        }
    }

    /**
     * Return a board as a string.
     *
     * @return String representing a board as a string.
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (board[row] == col) str.append(" Q ");
                else str.append(" - ");
            }
            str.append("\n");
        }
        return str.toString();
    }

    /**
     * @return Returns a hashCode value for this object.
     */
    public int hashCode() {
        return Objects.hashCode(this.board);
    }

    /**
     * Swap the columns of two queens.
     *
     * @param i Row of a queen.
     * @param j Row of a queen.
     */
    private void swapColumns(int i, int j) {
        int temp = board[i];
        board[i] = board[j];
        board[j] = temp;
    }

    /**
     * Calculates the difference of collisions before and after swapping two queens using the difference of diagonal collisions of the swap.
     *
     * @param i Row of a queen to simulate swap.
     * @param j Row of a queen to simulate swap.
     * @return Integer representing the difference between the sum of the affected diagonal collisions before and after the swap.
     */
    private int updateSlashCollisions(int i, int j) {
        int prevQFSlash = 0, prevQBSlash = 0;
        int aftQFSlash = 0, aftQBSlash = 0;
        int slope = calcSlope(i, board[i], j, board[j]);

        if (slope == 1 || slope == -1) {
            aftQFSlash++;
            aftQBSlash++;
        }

        // Before switch
        // For queen in row i
        int bFrontSlashIndexI = getFrontSlashIndex(i, board[i]);
        int bBackSlashIndexI = getBackSlashIndex(i, board[i]);
        prevQFSlash += (frontSlashCollisions[bFrontSlashIndexI] - 1);
        prevQBSlash += (backSlashCollisions[bBackSlashIndexI] - 1);

        // For queen in row j
        int bFrontSlashIndexJ = getFrontSlashIndex(j, board[j]);
        int bBackSlashIndexJ = getBackSlashIndex(j, board[j]);
        prevQFSlash += (frontSlashCollisions[bFrontSlashIndexJ] - 1);
        prevQBSlash += (backSlashCollisions[bBackSlashIndexJ] - 1);

        // After switch
        // For queen in row i
        int aFrontSlashIndexI = getFrontSlashIndex(i, board[j]);
        int aBackSlashIndexI = getBackSlashIndex(i, board[j]);
        aftQFSlash += (frontSlashCollisions[aFrontSlashIndexI]);
        aftQBSlash += (backSlashCollisions[aBackSlashIndexI]);

        // For queen in row j
        int aFrontSlashIndexJ = getFrontSlashIndex(j, board[i]);
        int aBackSlashIndexJ = getBackSlashIndex(j, board[i]);
        aftQFSlash += (frontSlashCollisions[aFrontSlashIndexJ]);
        aftQBSlash += (backSlashCollisions[aBackSlashIndexJ]);

        int dif = (aftQFSlash + aftQBSlash) - (prevQFSlash + prevQBSlash);
        return dif;
    }

    /**
     * Calculate the index of the diagonal in {@link #frontSlashCollisions} of a given queen.
     * @param row Row of a queen.
     * @param col Column of a queen.
     * @return Integer representing index of the queen's diagonal in {@link #frontSlashCollisions}.
     */
    private int getFrontSlashIndex(int row, int col) {
        return row + col;
    }

    /**
     * Calculate the index of the diagonal in {@link #backSlashCollisions} of a given queen.
     * @param row Row of a queen.
     * @param col Column of a queen.
     * @return Integer representing index of the queen's diagonal in {@link #backSlashCollisions}.
     */
    private int getBackSlashIndex(int row, int col) {
        return Math.abs(n - 1 - col + row);
    }

    /**
     * Check if two queens collide diagonally.
     * @param q1Row Row of first queen.
     * @param q1Col Column of first queen.
     * @param Q2row Row of second queen.
     * @param Q2col Column of second queen.
     * @return True if two queens collides, false otherwise.
     */
    private boolean checkDiagonalCollision(int q1Row, int q1Col, int Q2row, int Q2col) {
        int deltaRow = abs(q1Row - Q2row);
        int deltaCol = abs(q1Col - Q2col);
        return deltaCol == deltaRow;
    }

    /**
     * Calculates the slope between two queens.
     * @param q1Row Row of first queen.
     * @param q1Col Column of first queen.
     * @param Q2row Row of second queen.
     * @param Q2col Column of second queen.
     * @return Integer value representing the slope between two queens.
     */
    private int calcSlope(int q1Row, int q1Col, int Q2row, int Q2col) {
        int deltaRow = abs(q1Row - Q2row);
        int deltaCol = abs(q1Col - Q2col);
        return deltaCol / deltaRow;
    }

    /**
     * Check if two queens collide horizontally or vertically.
     * @param q1Row Row of first queen.
     * @param q1Col Column of first queen.
     * @param Q2row Row of second queen.
     * @param Q2col Column of second queen.
     * @return True if they collide, false otherwise.
     */
    private boolean checkAxisCollision(int q1Row, int q1Col, int Q2row, int Q2col) {
        return q1Col == Q2col || q1Row == Q2row;
    }

    private void updatedDiagonalCollisions() {
        if (this.frontSlashCollisions != null || this.backSlashCollisions != null) return;

        this.frontSlashCollisions = new int[NUMBER_OF_DIAGONALS];
        this.backSlashCollisions = new int[NUMBER_OF_DIAGONALS];
        for (int i = 0; i < n; i++) {
            int frontSlashIndex = getFrontSlashIndex(i, board[i]);
            int backSlashIndex = getBackSlashIndex(i, board[i]);
            frontSlashCollisions[frontSlashIndex]++;
            backSlashCollisions[backSlashIndex]++;
        }
    }

    /**
     * This method checks if a given queen <code>tempQueenC</code> attacks any other queen.
     * It iterates every other queen checking for collisions.
     *
     * @return Returns a boolean value. True if it collides, false otherwise.
     */
    private int collides() {
        int collisions = 0;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                if (checkDiagonalCollision(i, board[i], j, board[j])) collisions++;
        return collisions;
    }

    private int collides(int n) {
        int collisions = 0;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                if (checkDiagonalCollision(i, board[i], j, board[j])) collisions++;
        return collisions;
    }

    @Override
    public List<Ilayout> children() {
        updatedDiagonalCollisions();
        List<Ilayout> children = new ArrayList<>();
//        Ilayout temp = null;
//        int smallest = numOfCollisions;
        Board child;
        for (int i = 0; i < n; i++) {
//            int fColIndex = getFrontSlashIndex(i, board[i]);
//            int bColIndex = getBackSlashIndex(i, board[i]);
//            if (frontSlashCollisions[fColIndex] == 0 && backSlashCollisions[bColIndex] == 0) continue;

            for (int j = i + 1; j < n; j++) {
                int dif = updateSlashCollisions(i, j);
                //System.out.println("Dif: " + dif);
                if (dif < 0) {
                    child = new Board(this.n, this.board.clone(), numOfCollisions + dif);
                    child.swapColumns(i, j);
                    if (!child.isPossible()) continue;
                    children.add(child);
                    if (this.numOfCollisions + dif == 0) {
                        this.backSlashCollisions = null;
                        this.frontSlashCollisions = null;
                        return children;
                    }
                }

            }
        }

        this.frontSlashCollisions = null;
        this.backSlashCollisions = null;
        return children;
    }

    @Override
    public boolean isGoal() {
        return numOfCollisions == 0;
    }

    @Override
    public int getG() {
        return numOfCollisions;
    }

    @Override
    public boolean isPossible() {
        if (board == null) return false;
        return !((n % 2 == 0 && board[1] == 1));
    }
}