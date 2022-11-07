package models;

import java.util.Objects;

public class Board {
    private final int n;
    private final boolean board[][];

    public Board(int n) {
        this.board = new boolean[n][n];
        this.n = n;
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <n ; j++) {
                str = str + " " +board[i][j];
            }
        }
        return str;
    }

    public int hashCode() {
        return Objects.hashCode(this.board);
    }

}