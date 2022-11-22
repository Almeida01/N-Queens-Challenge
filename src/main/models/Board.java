package main.models;

import main.services.Ilayout;

import java.util.*;

import static java.lang.Math.abs;
import static java.util.Random.*;

public class Board implements Ilayout {
    private final static Random random = new Random();
    private final int n;

    /**
     * An integer array representing the board.\n
     * Each index of the array represents a row, where each index value represents the column where a queen is placed.
     */
    private int[] board;

    private int numOfCollisions = 0;
    private static int NUMBER_OF_DIAGONALS;

    private int[] frontSlashCollisions;
    private int[] backSlashCollisions;
    private boolean isFather = false;


    public Board(int n) {
        this.n = n;
        while (!isPossible()) fillBoard();
        //generateInitNQueens(1);
        NUMBER_OF_DIAGONALS = 2 * n - 1;
        numOfCollisions = collides();
        updatedDiagonalCollisions();
        isFather = true;
    }

    public Board(int n, int[] board, int numOfCollisions) {
        this.n = n;
        this.board = board;
        NUMBER_OF_DIAGONALS = 2 * n - 1;
        this.numOfCollisions = numOfCollisions;
    }

    private void generateInitNQueens(int p) {
        board = new int[n];
        int col = p;
        int row = 0;
        int save = col + 1;

        if (col % 2 == 0) {
            while (row < n) {
                if (col + 2 < n) {
                    board[row] = col + 2;
                    col = col + 2;
                } else {
                    if (save == 2) {
                        col = save;
                        save++;
                    } else {
                        col = 1;
                        save = 2;
                    }
                    board[row] = col;
                }
                row++;
            }
            if (board[0] == board[n - 1]) board[n - 1] = 0;
        } else {
            while (row < n) {
                if (col + 2 < n) {
                    board[row] = col + 2;
                    col += 2;
                } else {
                    if (save == 1) col = save++;
                    else {
                        col = 0;
                        save = 1;
                    }
                    board[row] = col;
                }
                row++;
            }
        }
    }

    private void fillBoard() {
        this.board = new Random()
                .ints(0, n)
                .distinct()
                .limit(n)
                .toArray();
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
        str.append("NUMBER OF COLLISIONS: ").append(numOfCollisions).append("\n");
//        str.append("Front: ").append(Arrays.toString(frontSlashCollisions)).append("\n");
//        str.append("Back: ").append(Arrays.toString(backSlashCollisions)).append("\n");

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
        int prevQFSlash = 0, prevQBSlash = 0;
        int aftQFSlash = 0, aftQBSlash = 0;
        int slope = calcSlope(i, board[i], j, board[j]);

//        if (slope == -1) aftQBSlash++;
//        else if (slope == 1) aftQFSlash++;
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

    private int getFrontSlashIndex(int row, int col) {
        return row + col;
    }

    private int getBackSlashIndex(int row, int col) {
        return Math.abs(n - 1 - col + row);
    }

    public boolean frontSlashCollision(int x, int y) {
        //y=x-n+/[i]



        return true;
    }

    private boolean checkDiagonalCollision(int tempQueenR, int tempQueenC, int Q2row, int Q2col) {
        int deltaRow = abs(tempQueenR - Q2row);
        int deltaCol = abs(tempQueenC - Q2col);
        return deltaCol == deltaRow;
    }

    private int calcSlope(int tempQueenR, int tempQueenC, int Q2row, int Q2col) {
        int deltaRow = abs(tempQueenR - Q2row);
        int deltaCol = abs(tempQueenC - Q2col);
        return deltaCol / deltaRow;
    }

    private boolean checkAxisCollision(int tempQueenR, int tempQueenC, int Q2row, int Q2col) {
        return tempQueenC == Q2col || tempQueenR == Q2row;
    }

    private void updatedDiagonalCollisions() {
        this.frontSlashCollisions = new int[NUMBER_OF_DIAGONALS];
        this.backSlashCollisions = new int[NUMBER_OF_DIAGONALS];
        for (int i = 0; i < n; i++) {
            int frontSlashIndex = getFrontSlashIndex(i, board[i]);
            int backSlashIndex = getBackSlashIndex(i, board[i]);
            frontSlashCollisions[frontSlashIndex]++;
            backSlashCollisions[backSlashIndex]++;
//            if (frontSlashCollisions[frontSlashIndex] > 1) numOfCollisions++;
//            if (backSlashCollisions[backSlashIndex] > 1) numOfCollisions++;
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
        if (!isFather) updatedDiagonalCollisions();
        List<Ilayout> children = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int dif = updateSlashCollisions(i, j);
                if (dif < 0) {
                    System.out.println("Dif: " + dif);
                    if (!isPossible()) continue;
                    swapColumns(i, j);
                    children.add(new Board(this.n, this.board.clone(), numOfCollisions + dif));
                    swapColumns(i, j);
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