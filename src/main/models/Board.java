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

    public Board(int n) {
        this.n = n;
        this.board = new int[n];
    }

    public Board(int n, int[] board, int currentLine) {
        this.n = n;
        this.board = board;
        this.currentLine = currentLine;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        int row = 0, col = 0;
        while (row < currentLine) {
            if (col != board[row]) str.append(" - ");
            else str.append(" Q ");
            col++;
            if (col == n) {
                str.append('\n');
                col = 0;
                row++;
            }
        }

        while (row < n) {
            str.append(" - ");
            col++;
            if (col == n) {
                str.append('\n');
                col = 0;
                row++;
            }
        }
        return str.toString();
    }

    public int hashCode() {
        return Objects.hashCode(this.board);
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
     * @param tempQueenR Row of queen being placed.
     * @return Returns a boolean value. True if it collides, false otherwise.
     */
    private boolean collides(int tempQueenR) {
        int tempQueenC = board[tempQueenR]; // Column of temporary queen
        for (int row = 0; row < currentLine; row++) {
            int qCol = board[row];
            if (checkAxisCollision(tempQueenR, tempQueenC, row, qCol) || checkDiagonalCollision(tempQueenR, tempQueenC, row, qCol))
                return true;
        }
        return false;
    }

    @Override
    public List<Ilayout> children() {
        List<Ilayout> children = new ArrayList<>();
        for (int col = 0; col < n; col++) {
            board[currentLine] = col;
            if (!collides(currentLine)) children.add(new Board(this.n, this.board.clone(),currentLine + 1));
        }
        return children;
    }

    @Override
    public boolean isGoal(Ilayout l) {
        return currentLine == n;
    }

    @Override
    public double getG() {
        return 0;
    }

    @Override
    public boolean isPossible(Ilayout goal) {
        return false;
    }

}