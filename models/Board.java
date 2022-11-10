package models;

import java.util.Objects;

public class Board {
    private final int n;
    private final boolean board[][];

    public Board(int n) {
        this.board = new boolean[n][n];
        this.n = n;
    }

    public void addQueen(int x, int y) {
        if (this.validate(x,y)) board[x][y] = true;
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

    private boolean validate(int x, int y) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < y; j++) {
                if(!board[i][j] && (x + j == y + i || x + y == i + j || x == i))
                    return false;
            }
        }
        board[x][y] = true;    
        return true;
    }


    public int hashCode() {
        return Objects.hashCode(this.board);
    }

}