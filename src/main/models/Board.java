package main.models;

import main.services.Ilayout;

import java.util.*;

import static java.lang.Math.abs;

public class Board implements Ilayout {
    private final int n;

    /**
     * An integer array representing the board.\n
     * Each index of the array represents a row, where each index value represents the column where a queen is placed.
     */
    private final int[] board;
    private int currentLine = 0;

    private int numOfCollisions;
    private static int NUMBER_OF_DIAGONALS;

    private final int[] frontSlashCollisions;
    private final int[] backSlashCollisions;


    public Board(int n) {
        this.n = n;
        this.board = new Random()
                .ints(0, n)
                .distinct()
                .limit(n)
                .toArray();
        NUMBER_OF_DIAGONALS = 2 * n - 1;
        this.frontSlashCollisions = new int[NUMBER_OF_DIAGONALS];
        this.backSlashCollisions = new int[NUMBER_OF_DIAGONALS];
        numOfCollisions = collides();
    }

    public Board(int n, int[] board, int numOfCollisions) {
        this.n = n;
        this.board = board;
        NUMBER_OF_DIAGONALS = 2 * n - 1;
        this.frontSlashCollisions = new int[NUMBER_OF_DIAGONALS];
        this.backSlashCollisions = new int[NUMBER_OF_DIAGONALS];
        this.numOfCollisions = numOfCollisions;
        updatedDiagonalCollisions();

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
        str.append("FRONT SLASH COLLISIONS = ").append(Arrays.toString(frontSlashCollisions)).append("\n");
        str.append("BACK SLASH COLLISIONS = ").append(Arrays.toString(backSlashCollisions)).append("\n");
        str.append("NUMBER OF COLLISIONS: ").append(numOfCollisions).append("\n");
        return str.toString();
    }

    public int getNumOfCollisions() {
        return numOfCollisions;
    }

    public int hashCode() {
        return Objects.hashCode(this.board);
    }

    private void swapColumns(int i, int j) {
        int temp = board[i];
        board[i] = board[j];
        board[j] = temp;
    }

    private int updateSlashCollisions(int i, int j) {
        int[] diagonals = new int[4];
        // Before switch
        // For queen in row i
        int bFrontSlashIndexI = getFrontSlashIndex(i, board[i]);
        int bBackSlashIndexI = getBackSlashIndex(i, board[i]);
        diagonals[0] = (frontSlashCollisions[bFrontSlashIndexI] - 1) + (backSlashCollisions[bBackSlashIndexI] - 1);

        // For queen in row j
        int bFrontSlashIndexJ = getFrontSlashIndex(j, board[j]);
        int bBackSlashIndexJ = getBackSlashIndex(j, board[j]);
        diagonals[1] = (frontSlashCollisions[bFrontSlashIndexJ] - 1) + (backSlashCollisions[bBackSlashIndexJ] - 1);

        // After switch
        // For queen in row i
        int aFrontSlashIndexI = getFrontSlashIndex(i, board[j]);
        int aBackSlashIndexI = getBackSlashIndex(i, board[j]);
        diagonals[2] = (frontSlashCollisions[aFrontSlashIndexI] + 1) + (backSlashCollisions[aBackSlashIndexI] + 1);
        // For queen in row j
        int aFrontSlashIndexJ = getFrontSlashIndex(j, board[i]);
        int aBackSlashIndexJ = getBackSlashIndex(j, board[i]);
        diagonals[3] = (frontSlashCollisions[aFrontSlashIndexJ] + 1) + (backSlashCollisions[aBackSlashIndexJ] + 1);

        int dif = (diagonals[2] + diagonals[3]) - (diagonals[0] + diagonals[1]);
        return dif;
    }

    /*private int updateSlashCollisions(int i, int j) {
        // Old position: (R, C) -> (i, board[i])
        // New position: (R, C) -> (i, board[j])
        int bFrontSlashIndexI = getFrontSlashIndex(i, board[i]);
        int bBackSlashIndexI = getBackSlashIndex(i, board[i]);
        int bFrontSlashIndexJ = getFrontSlashIndex(j, board[j]);
        int bBackSlashIndexJ = getBackSlashIndex(j, board[j]);

        // Get collisions in diagonals before swap
        int fI = frontSlashCollisions[bFrontSlashIndexI] > 1 ? frontSlashCollisions[bFrontSlashIndexI] - 1 : 0;
        int bI = backSlashCollisions[bBackSlashIndexI] > 1 ? backSlashCollisions[bBackSlashIndexI] - 1 : 0;
        int fJ = frontSlashCollisions[bFrontSlashIndexJ] > 1 ? frontSlashCollisions[bFrontSlashIndexJ] - 1 : 0;
        int bJ = backSlashCollisions[bBackSlashIndexJ] > 1 ? backSlashCollisions[bBackSlashIndexJ] - 1 : 0;

        int before = fI + bI + fJ + bJ;
        if (bFrontSlashIndexI == bFrontSlashIndexJ) before -= fI;
        else if (bBackSlashIndexI == bBackSlashIndexJ) before -= bI;

        int aFrontSlashIndexI = getFrontSlashIndex(i, board[j]);
        int aBackSlashIndexI = getBackSlashIndex(i, board[j]);
        int aFrontSlashIndexJ = getFrontSlashIndex(j, board[i]);
        int aBackSlashIndexJ = getBackSlashIndex(j, board[i]);

        // Get collisions in diagonals after swap
        fI = frontSlashCollisions[aFrontSlashIndexI] + 1 > 1 ? frontSlashCollisions[aFrontSlashIndexI] : 0;
        bI = backSlashCollisions[aBackSlashIndexI] + 1 > 1 ? backSlashCollisions[aBackSlashIndexI] : 0;
        fJ = frontSlashCollisions[aFrontSlashIndexJ] + 1 > 1 ? frontSlashCollisions[aFrontSlashIndexJ] : 0;
        bJ = backSlashCollisions[aBackSlashIndexJ] + 1 > 1 ? backSlashCollisions[aBackSlashIndexJ] : 0;

        // Get collisions in diagonals after swap
        int after = fI + bI + fJ + bJ;
        if (aFrontSlashIndexI == aFrontSlashIndexJ) after -= fI;
        else if (aBackSlashIndexI == aBackSlashIndexJ) after -= bI;

        return after - before;
    }*/


    private int getFrontSlashIndex(int row, int col) {
        return row + col;
    }

    private int getBackSlashIndex(int row, int col) {
        return Math.abs(n - 1 - col + row);
    }

    private boolean checkDiagonalCollision(int tempQueenR, int tempQueenC, int Q2row, int Q2col) {
        int deltaRow = abs(tempQueenR - Q2row);
        int deltaCol = abs(tempQueenC - Q2col);
        return deltaCol == deltaRow;
    }

    private boolean checkAxisCollision(int tempQueenR, int tempQueenC, int Q2row, int Q2col) {
        return tempQueenC == Q2col || tempQueenR == Q2row;
    }

    private void updatedDiagonalCollisions() {
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
        for (int i = 0; i < n; i++) {
            int frontSlashIndex = getFrontSlashIndex(i, board[i]);
            int backSlashIndex = getBackSlashIndex(i, board[i]);
            frontSlashCollisions[frontSlashIndex]++;
            backSlashCollisions[backSlashIndex]++;

            for (int j = i + 1; j < n; j++) {
                if (checkAxisCollision(i, board[i], j, board[j])
                        || checkDiagonalCollision(i, board[i], j, board[j])) {
                    collisions++;
                }
            }
        }
        return collisions;
    }

    @Override
    public List<Ilayout> children() {
        List<Ilayout> children = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int dif = updateSlashCollisions(i, j);
                if (dif <= 0) {
                    swapColumns(i, j);
                    children.add(new Board(this.n, this.board.clone(), this.numOfCollisions + dif));
                    swapColumns(i, j);
                }

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