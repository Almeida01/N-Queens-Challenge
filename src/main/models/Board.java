package main.models;

import main.services.Ilayout;

import java.util.*;
import java.util.concurrent.atomic.AtomicLongArray;

import static java.lang.Math.abs;

public class Board implements Ilayout, Cloneable {
    private final int n;

    /**
     * An integer array representing the board.\n
     * Each index of the array represents a row, where each index value represents the column where a queen is placed.
     */
    private final int[] board;

    private int currentLine = 0;
    private int numOfCollisions;

    public Board(int n) {
        this.n = n;
        this.board = new Random()
                .ints(0, n)
                .distinct()
                .limit(n)
                .toArray();
        numOfCollisions = collides(board);
    }

    public Board(int n, int[] board) {
        this.n = n;
        this.board = board;
        numOfCollisions = collides(board);

    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (board[row] == col) str.append(" Q ");
                else str.append(" - ");
            }
            str.append("\n");
        }
        str.append("NUMBER OF COLLISIONS: " + numOfCollisions + "\n");
        return str.toString();
    }

    public int hashCode() {
        return Objects.hashCode(this.board);
    }

    private int[] swapColumns(int i, int j) {
        int[] swappedBoard = board.clone();
        swappedBoard[i] = board[j];
        swappedBoard[j] = board[i];
        return swappedBoard;
    }

    private boolean checkDiagonalCollision(int tempQueenR, int tempQueenC, int Q2row, int Q2col) {
        int deltaRow = abs(tempQueenR - Q2row);
        int deltaCol = abs(tempQueenC - Q2col);
        return deltaCol == deltaRow;
    }

    private boolean checkAxisCollision(int tempQueenR, int tempQueenC, int Q2row, int Q2col) {
        return tempQueenR == Q2row || tempQueenC == Q2col;
    }

    /**
     * This method checks if a given queen <code>tempQueenC</code> attacks any other queen.
     * It iterates every other queen checking for collisions.
     *
     * @return Returns a boolean value. True if it collides, false otherwise.
     */
    private int collides(int[] board) {
        int collisions = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (checkAxisCollision(i, board[i], j, board[j])
                        || checkDiagonalCollision(i, board[i], j, board[j]))
                    collisions++;
            }
        }
        /*int tempQueenC = board[tempQueenR]; // Column of temporary queen
        for (int row = 0; row < currentLine; row++) {
            int qCol = board[row];
            if (checkAxisCollision(tempQueenR, tempQueenC, row, qCol) || checkDiagonalCollision(tempQueenR, tempQueenC, row, qCol))
                collisions++;
        }*/
        return collisions;
    }

    @Override
    public List<Ilayout> children() {
        List<Ilayout> children = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int[] x = swapColumns(i, j);
                if (collides(x) < numOfCollisions)
                    children.add(new Board(this.n, x));
            }
        }
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
    public boolean isPossible(Ilayout goal) {
        return false;
    }

}