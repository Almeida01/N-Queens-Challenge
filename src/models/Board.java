package src.models;

import java.util.Objects;
import java.util.Scanner;

public class Board {
    private final int n;
    private final boolean board[][];

    public Board(int n) {
        this.board = new boolean[n][n];
        this.n = n;
    }

    public void addQueen(int x, int y) {
        if (validate(x,y)) board[x][y] = true;
    }

    public boolean validate(int x, int y) {
        //ToDo
        return true;
    }

    public void printSolution() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) System.out.print(" " + board[i][j] + " ");
            System.out.println();
        }
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <n ; j++) {
                str = str + " " + board[i][j];
            }
        }
        return str;
    }

    public int hashCode() {
        return Objects.hashCode(this.board);
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        Board b1 = new Board(sc.nextInt());

        b1.printSolution();
        sc.close();
    }

}