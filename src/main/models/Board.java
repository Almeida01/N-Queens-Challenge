package main.models;

import java.util.BitSet;
import java.util.Objects;

public class Board {
    private final int n;
    private final int dim;
    private final BitSet board;

    public Board(int n) {
        this.n = n;
        this.dim = n*n;
        this.board = new BitSet(dim);
        board.set(3);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        int newLine = 1;
        for (int i = 0; i < dim; i++) {
            if (board.get(i)) str.append(" Q ");
            else str.append(" - ");

            if(newLine == 4) {
                str.append('\n');
                newLine = 1;
            } else newLine++;
        }
        return str.toString();
    }

    public int hashCode() {
        return Objects.hashCode(this.board);
    }
}